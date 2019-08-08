package com.stt.service.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceProviderController {

	@Value("${server.port}")
	private String port;

	@GetMapping("/echo/{message}")
	public String echo(@PathVariable(value = "message") String msg){
		return "hello: "+msg +" port:"+port;
	}

}
