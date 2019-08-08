package com.stt.service.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceProviderController {

	@Value("${server.port}")
	private String port;

	@Autowired
	private ConfigurableApplicationContext configurableApplicationContext;


	@GetMapping("/echo/{message}")
	public String echo(@PathVariable(value = "message") String msg){
		return "hello: "+msg +" port:"+port;
	}

	@GetMapping("/hi")
	public String hi(){
		return configurableApplicationContext.getEnvironment().getProperty("user.name");
	}


}
