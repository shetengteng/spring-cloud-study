package com.stt.server.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

	@Value("${server.port}")
	private Integer port;

	@GetMapping("/hi")
	public String hi(@RequestParam("msg") String msg){
		return String.format("message is: %s and port is: %s",msg,port);
	}
}
