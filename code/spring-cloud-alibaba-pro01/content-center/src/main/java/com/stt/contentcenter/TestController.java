package com.stt.contentcenter;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.stt.contentcenter.dao.content.ShareMapper;
import com.stt.contentcenter.domain.dto.user.UserDTO;
import com.stt.contentcenter.domain.entity.content.Share;
import com.stt.contentcenter.feign.client.TestBaiduFeignClient;
import com.stt.contentcenter.feign.client.TestUserCenterFeignClient;
import com.stt.contentcenter.rocketmq.MySource;
import com.stt.contentcenter.sentinel.test.TestControllerBlockHandlerClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/test")
@RefreshScope
public class TestController {

	@Autowired
	private ShareMapper shareMapper;

	@GetMapping("/share")
	public List testInsert(){
		// 插入
		Share share = new Share();
		share.setCreateTime(new Date());
		share.setUpdateTime(new Date());
		share.setTitle("xxx");
		share.setAuthor("stt");
		share.setCover("fff");
		share.setBuyCount(1);
		shareMapper.insertSelective(share);

		// 查询
		List<Share> shares = shareMapper.selectAll();
		return shares;
	}

	@Autowired
	private DiscoveryClient discoveryClient;

	@GetMapping("/discovery")
	public List<ServiceInstance> discoveryClient(){
		// 查询服务的所有用户微服务的实例的信息
		List<ServiceInstance> instances = discoveryClient.getInstances(
				"user-center");

		// 查询注册了哪些微服务
		// List<String> services = discoveryClient.getServices();

		return instances;
	}

	@Autowired
	private TestUserCenterFeignClient testUserCenterFeignClient;

	@GetMapping("/q")
	public UserDTO query(UserDTO userDTO){
		return testUserCenterFeignClient.query(userDTO);
	}

	@Autowired
	private TestBaiduFeignClient testBaiduFeignClient;

	@GetMapping("/baidu")
	public String testBaidu(){
		return testBaiduFeignClient.index();
	}

	// 造成关联限流
	public static void main(String[] args) throws InterruptedException {
		RestTemplate re = new RestTemplate();
		for (int i = 0; i < 1000; i++) {
			re.getForObject("http://localhost:8010/actuator/sentinel",String.class);
			Thread.sleep(400);
		}
	}

	@Autowired
	private	TestService testService;

	@GetMapping("/a")
	public String testA(){
		testService.common();
		return "testA";
	}

	@GetMapping("/b")
	public String testB(){
		testService.common();
		return "testB";
	}

	// 热点规则
	@GetMapping("/hot")
	@SentinelResource("hot")
	public String testHot(
			@RequestParam(required = false,name = "a") String a,
			@RequestParam(required = false,name = "b") String b){
		return a+":"+b;
	}

	@GetMapping("/addflowrule")
	public String testFlowQPSRule(){
		this.initFlowQpsRule();
		return "rule";
	}

	private void initFlowQpsRule() {
		List<FlowRule> rules = new ArrayList<>();
		FlowRule rule = new FlowRule("/shares/1");
		// set limit qps to 20
		rule.setCount(20);
		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		rule.setLimitApp("default");
		rules.add(rule);
		FlowRuleManager.loadRules(rules);
	}

	@GetMapping("/sentinelapi")
	public String testSentinelAPI(
			@RequestParam(required = false) String a){
		// 定义一个sentinel保护的资源，名称为test-sentinel-api 要求唯一

		String resourceName = "test-sentinel-api";
		// 指定来源 test-xxx
		ContextUtil.enter(resourceName,"test-xxx");
		Entry entry = null;
		try {
			entry = SphU.entry(resourceName);
			// 被保护的业务逻辑
			if(StringUtils.isBlank(a)){
				throw new IllegalArgumentException("不能为空");
			}

			return a;
		} catch (BlockException e) {
			// 如果被保护的资源被限流或者降级了，则抛出该异常
			e.printStackTrace();
			log.warn("限流，降级 {}", e);
			return "限流降级";
		}catch (IllegalArgumentException e2){
			Tracer.trace(e2);
			return "异常降级";
		}finally {
			if(entry != null){
				entry.exit();
			}
			ContextUtil.exit();
		}
	}

	@GetMapping("/sentinelapi2")
	// 注解不支持来源
	// 默认会捕获所有的异常
	@SentinelResource(
			value = "test-sentinel-api2",
			blockHandler = "block",
			fallback = "fallback")
	public String testSentinelAPI2(
			@RequestParam(required = false) String a){

		// 业务方法
		if(StringUtils.isBlank(a)){
			throw new IllegalArgumentException("不能为空");
		}
		return a;
	}

	// blockHandler对应的方法，需要入参和返回值同调用方法基本一致
	public String block(String a,BlockException e){
		// 如果被保护的资源被限流或者降级了，则抛出该异常
		log.warn("限流，降级 {}", e);
		return "限流 block";
	}

	//处理降级，sentinel1.6可以传throwable类型参数
	public String fallback(String a){
		return "降级";
	}

	@GetMapping("/sentinelapi3")
	// 注解不支持来源
	// 默认会捕获所有的异常
	@SentinelResource(
			value = "test-sentinel-api3",
			blockHandler = "block",
			blockHandlerClass = TestControllerBlockHandlerClass.class,
			fallback = "fallback")
	public String testSentinelAPI3(
			@RequestParam(required = false) String a){

		// 业务方法
		if(StringUtils.isBlank(a)){
			throw new IllegalArgumentException("不能为空");
		}
		return a;
	}

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/sentinelRestTemplate/{userId}")
	public UserDTO testSentinelRestTemplate(@PathVariable Integer userId){
		return this.restTemplate.getForObject(
				"http://user-center/users/{userId}",
				UserDTO.class,
				userId
		);
	}

	@GetMapping("/sentinelRestTemplate/token/{userId}")
	public ResponseEntity<UserDTO> testSentinelRestTemplateHasToken(@PathVariable Integer userId){

		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttributes;
		HttpServletRequest request = attributes.getRequest();
		// 前端在header中放入token
		String token = request.getHeader("X-Token");

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Token",token);

		ResponseEntity<UserDTO> re = this.restTemplate.exchange(
				"http://user-center/users/{userId}",
				HttpMethod.GET,
				new HttpEntity<>(headers),
				UserDTO.class,
				userId
		);
		return re;
	}

	@Autowired(required = false)
	private Source source;

	@GetMapping("/stream")
	public String testStream(){
		this.source.output().send(
				MessageBuilder.withPayload("测试消息").build()
		);
		return "success";
	}


	@Autowired(required = false)
	private MySource mySource;

	@GetMapping("/stream2")
	public String testStream2(){
		this.mySource.output().send(
				MessageBuilder.withPayload("测试消息2").build()
		);
		return "success";
	}

	@Value("${your.configuration}")
	private String nacosConfig;


	@GetMapping("/config")
	public String getConfig(){
		return nacosConfig;
	}

}