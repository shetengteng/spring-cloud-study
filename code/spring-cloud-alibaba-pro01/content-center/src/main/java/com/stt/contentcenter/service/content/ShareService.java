package com.stt.contentcenter.service.content;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stt.contentcenter.dao.content.RocketmqTransactionLogMapper;
import com.stt.contentcenter.dao.content.ShareMapper;
import com.stt.contentcenter.domain.dto.share.ShareAuditDTO;
import com.stt.contentcenter.domain.dto.share.ShareDTO;
import com.stt.contentcenter.domain.dto.user.UserDTO;
import com.stt.contentcenter.domain.entity.content.RocketmqTransactionLog;
import com.stt.contentcenter.domain.entity.content.Share;
import com.stt.contentcenter.domain.enums.AuditStatusEnum;
import com.stt.contentcenter.domain.msg.UserAddBonusMsgDTO;
import com.stt.contentcenter.feign.client.UserCenterFeignClient;
import com.stt.contentcenter.feign.client.UserCenterFeignHasTokenClient;
import com.stt.contentcenter.rocketmq.MySource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ShareService {

	@Autowired
	private ShareMapper shareMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	private UserCenterFeignClient userCenterFeignClient;

	@Autowired
	private UserCenterFeignHasTokenClient userCenterFeignHasTokenClient;


	public ShareDTO findByIdFromFeignAndToken(Integer id,String token){
		// 获取分享详情
		Share share = shareMapper.selectByPrimaryKey(id);
		// 获取userId然后获取user信息
		Integer userId = share.getUserId();

		UserDTO userDTO = userCenterFeignHasTokenClient.findById(id,token);

		ShareDTO shareDTO = new ShareDTO();
		// 消息的装配
		BeanUtils.copyProperties(share,shareDTO);
		shareDTO.setWxNickname(userDTO.getWxNickname());

		return shareDTO;
	}

	public ShareDTO findByIdFromFeign(Integer id){
		// 获取分享详情
		Share share = shareMapper.selectByPrimaryKey(id);
		// 获取userId然后获取user信息
		Integer userId = share.getUserId();

		UserDTO userDTO = userCenterFeignClient.findById(id);

		ShareDTO shareDTO = new ShareDTO();
		// 消息的装配
		BeanUtils.copyProperties(share,shareDTO);
		shareDTO.setWxNickname(userDTO.getWxNickname());

		return shareDTO;
	}

	public ShareDTO findByIdFromRibbon(Integer id){
		// 获取分享详情
		Share share = shareMapper.selectByPrimaryKey(id);
		// 获取userId然后获取user信息
		Integer userId = share.getUserId();

		// ribbon会将user-center通过nacos转为ip和port进行解析
		UserDTO userDTO = restTemplate.getForObject(
				"http://user-center/users/{id}",
				UserDTO.class, userId
		);

		ShareDTO shareDTO = new ShareDTO();
		// 消息的装配
		BeanUtils.copyProperties(share,shareDTO);
		shareDTO.setWxNickname(userDTO.getWxNickname());

		return shareDTO;
	}

	public ShareDTO findById(Integer id){
		// 获取分享详情
		Share share = shareMapper.selectByPrimaryKey(id);
		// 获取userId然后获取user信息
		Integer userId = share.getUserId();

		// 手动方式使用nacos的注册信息
		List<ServiceInstance> instances = discoveryClient.getInstances("user-center");

		// 手动随机
		List<String> urls = instances.stream()
				.map(instance -> instance.getUri().toString())
				.collect(Collectors.toList());

		if(CollectionUtils.isEmpty(urls)){
			throw new IllegalArgumentException("结果为空");
		}

		String url = urls.get(new Random().nextInt(urls.size()));


		// 将json转换为dto
		UserDTO userDTO = restTemplate.getForObject(
				url+"users/{id}",
				UserDTO.class, userId
		);

		log.info("请求地址{}",url);
		ShareDTO shareDTO = new ShareDTO();
		// 消息的装配
		BeanUtils.copyProperties(share,shareDTO);
		shareDTO.setWxNickname(userDTO.getWxNickname());

		return shareDTO;
	}

	// 测试restTemplate
	public static void main(String[] args) {
		// get请求
		RestTemplate restTemplate = new RestTemplate();
		String re = restTemplate.getForObject(
				"http://localhost:8080/users/1", String.class
		);
		System.out.println(re);

		// 支持占位符调用
		String re2 = restTemplate.getForObject(
				"http://localhost:8080/users/{id}",
				String.class,
				2
		);
		System.out.println(re2);

		// 功能与上等价
		ResponseEntity<String> entity = restTemplate.getForEntity(
				"http://localhost:8080/users/{id}",
				String.class,
				2
		);
		System.out.println(entity.getBody());
		// entity可以得到响应码
		System.out.println(entity.getStatusCode());

	}

	@Autowired
	private RocketMQTemplate rocketMQTemplate;

	@Transactional(rollbackFor = Exception.class)
	public Share auditById(Integer id, ShareAuditDTO shareAuditDTO) {
		// 查询share是否存在，不存在或者audit_status != NOT_YET
		Share share = this.shareMapper.selectByPrimaryKey(id);
		if(share == null){
			throw new IllegalArgumentException("参数非法，该分享不存在");
		}
		if(!Objects.equals("NOT_YET",share.getAuditStatus())){
			throw new IllegalArgumentException("参数非法，该分享已审核通过，或审核不通过");
		}
		// 审核资源，设置状态为PASS/REJECT
		share.setAuditStatus(shareAuditDTO.getAuditStatusEnum().toString());
		share.setReason(shareAuditDTO.getReason());
		this.shareMapper.updateByPrimaryKey(share);

		// 如果是PASS，则添加积分,添加积分可能是异步的
		// 发送消息给rocketmq
		// 参数1 topic 参数2 消息体
		rocketMQTemplate.convertAndSend("add-bonus",
				UserAddBonusMsgDTO.builder()
						.userId(share.getUserId())
						.bonus(50).
						build()
		);
		return share;
	}

	/**
	 * 分布式事务实现
	 * @param id
	 * @param shareAuditDTO
	 * @return
	 */
	public Share auditById2(Integer id, ShareAuditDTO shareAuditDTO) {
		// 查询share是否存在，不存在或者audit_status != NOT_YET
		Share share = this.shareMapper.selectByPrimaryKey(id);
		if(share == null){
			throw new IllegalArgumentException("参数非法，该分享不存在");
		}
		if(!Objects.equals("NOT_YET",share.getAuditStatus())){
			throw new IllegalArgumentException("参数非法，该分享已审核通过，或审核不通过");
		}

		// 如果是PASS，那么发送消息给rocketmq 让用户中心消费
		if(AuditStatusEnum.PASS.equals(shareAuditDTO.getAuditStatusEnum())){
			// 发送半消息 rocketMQ的事务机制
			this.rocketMQTemplate.sendMessageInTransaction(
					"tx-add-bonus-group",//group
					"add-bonus",//topic
					MessageBuilder.withPayload(
							UserAddBonusMsgDTO.builder()
							.userId(share.getUserId())
							.bonus(50).build())
						.setHeader(RocketMQHeaders.TRANSACTION_ID, UUID.randomUUID().toString()) // 可以设置header
						.setHeader("shared_id",id) // header有关键作用
						.build(), // 消息体
					shareAuditDTO // arg
			);
		}else {
			this.auditByIdInDB(id,shareAuditDTO);
		}
		return share;
	}

	@Transactional(rollbackFor = Exception.class)
	public void auditByIdInDB(Integer id,ShareAuditDTO shareAuditDTO){
		Share share = new Share();
		share.setId(id);
		// 审核资源，设置状态为PASS/REJECT
		share.setAuditStatus(shareAuditDTO.getAuditStatusEnum().toString());
		share.setReason(shareAuditDTO.getReason());
		this.shareMapper.updateByPrimaryKeySelective(share);
		// todo 把share写入缓存
	}

	@Autowired
	RocketmqTransactionLogMapper rocketmqTransactionLogMapper;

	@Transactional(rollbackFor = Exception.class)
	public void aduitByIdWithRocketMqLog(Integer id,ShareAuditDTO auditDTO,String transactionId){
		this.auditByIdInDB(id,auditDTO);
	// 对本地事务进行rocketmq的日志记录
		rocketmqTransactionLogMapper.insertSelective(
				RocketmqTransactionLog.builder()
						.transactionId(transactionId)
						.log("审核分享")
						.build()
		);
	}

	@Autowired
	private MySource mySource;

	public Share auditById3(Integer id, ShareAuditDTO shareAuditDTO) {
		// 查询share是否存在，不存在或者audit_status != NOT_YET
		Share share = this.shareMapper.selectByPrimaryKey(id);
		if(share == null){
			throw new IllegalArgumentException("参数非法，该分享不存在");
		}
		if(!Objects.equals("NOT_YET",share.getAuditStatus())){
			throw new IllegalArgumentException("参数非法，该分享已审核通过，或审核不通过");
		}

		// 如果是PASS，那么发送消息给rocketmq 让用户中心消费
		if(AuditStatusEnum.PASS.equals(shareAuditDTO.getAuditStatusEnum())){
			// 发送半消息 rocketMQ的事务机制
			mySource.txOutput().send(
					MessageBuilder.withPayload(
							UserAddBonusMsgDTO.builder()
									.userId(share.getUserId())
									.bonus(50).build())
							.setHeader(RocketMQHeaders.TRANSACTION_ID, UUID.randomUUID().toString()) // 可以设置header
							.setHeader("shared_id",id) // header有关键作用
							.setHeader("auditDTO", JSON.toJSONString(shareAuditDTO))
							.build() // 消息体
			);
		}else {
			this.auditByIdInDB(id,shareAuditDTO);
		}
		return share;
	}

	public PageInfo<Share> q(String title, Integer pageNo, Integer pageSize) {
//		它会切入下面这条不分页的sql，自动拼接分页的sql，使用mybatis的拦截器，自动加上limit语句
		PageHelper.startPage(pageNo,pageSize);
//		不分页的sql
		List<Share> shares = this.shareMapper.selectByParam(title);

		return new PageInfo<Share>(shares);
	}
}