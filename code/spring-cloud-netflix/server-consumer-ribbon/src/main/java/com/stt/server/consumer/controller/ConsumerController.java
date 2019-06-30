package com.stt.server.consumer.controller;

import com.stt.server.consumer.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

	@Autowired
	private ConsumerService consumerService;

	@GetMapping("/hi")
	public String hi(String msg){
		return consumerService.hi(msg);
	}
}
