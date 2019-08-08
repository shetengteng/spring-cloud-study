package com.stt.service.consumer.controller;

import com.stt.service.consumer.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceConsumerController {

	@Autowired
	private EchoService echoService;

	@Value("${spring.application.name}")
	private String appName;

	@GetMapping(value = "/echo/app/name")
	public String echo() {
		return echoService.echo(appName);
	}
}
