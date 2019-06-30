package com.stt.server.consumer.feign.service;

import org.springframework.stereotype.Component;

// 注意必须要加入到容器中，使用Component注解
@Component
public class ConsumerServiceHystrix implements ConsumerService{

	@Override
	public String hi(String msg) {
		return "Hi，your message is :\"" + msg + "\" but request error.";
	}
}
