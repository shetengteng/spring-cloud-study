package com.stt.contentcenter.rocketmq;

import com.alibaba.fastjson.JSON;
import com.stt.contentcenter.dao.user.BonusEventLogMapper;
import com.stt.contentcenter.dao.user.UserMapper;
import com.stt.contentcenter.domain.entity.user.BonusEventLog;
import com.stt.contentcenter.domain.entity.user.User;
import com.stt.contentcenter.domain.entity.user.dto.msg.UserAddBonusMsgDTO;
import com.stt.contentcenter.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Service;

import java.util.Date;

// 接口的泛型是消息体，消费者的group写在代码中
@Slf4j
@Service
public class AddBonusListenerByStream {

	@Autowired
	UserService userService;

	@StreamListener(MySink.MY_TX_INPUT)
	public void receive(UserAddBonusMsgDTO userAddBonusMsgDTO){
		// 是一个事务
		userService.addBonus(userAddBonusMsgDTO);
		log.info("积分添加完毕...2");
	}
	
}
