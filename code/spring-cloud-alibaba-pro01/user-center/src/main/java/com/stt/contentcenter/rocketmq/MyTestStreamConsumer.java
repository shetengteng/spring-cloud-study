package com.stt.contentcenter.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ErrorMessage;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MyTestStreamConsumer {

	@StreamListener(MySink.MY_INPUT)
	public void receive(String msgBody){
		log.info("自定义接口消费：{}",msgBody);
		throw new IllegalArgumentException("模拟抛异常");
	}

	// 全局异常处理
	// 发生异常的消息
	@StreamListener("errorChannel")
	public void error(Message<?> msg){
		ErrorMessage errorMessage = (ErrorMessage) msg;
		log.warn("发生异常：{}",errorMessage);
	}

}
