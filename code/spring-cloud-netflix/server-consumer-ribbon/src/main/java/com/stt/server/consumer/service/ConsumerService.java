package com.stt.server.consumer.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ConsumerService {

	@Autowired
	private RestTemplate restTemplate;

	// 在调用方法上添加熔断
	@HystrixCommand(fallbackMethod="hiError")
	public String hi(String msg){
		return restTemplate.getForObject("http://server-provider/hi?msg="+msg,String.class);
	}

	public String hiError(String message) {
		return "Hi，your message is :\"" + message + "\" but request error.";
	}

}
