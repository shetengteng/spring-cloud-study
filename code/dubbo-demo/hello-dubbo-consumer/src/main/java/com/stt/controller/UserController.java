package com.stt.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.stt.dubbo.demo.service.user.api.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Reference(version = "${user.service.version}")
	private UserService userService;

	// 阈值 2s内10次调用失败则熔断
	@HystrixCommand(
			fallbackMethod = "hiError"
			,commandProperties = {
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
	})
	@GetMapping("sayHi")
	public String sayHi(){
		return userService.sayHi();
	}

	public String hiError(){
		return "hi error";
	}

}
