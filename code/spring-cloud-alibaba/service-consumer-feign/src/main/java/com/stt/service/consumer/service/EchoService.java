package com.stt.service.consumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-provider",fallback = EchoServiceFallBack.class)
public interface EchoService {

	@GetMapping(value = "/echo/{message}")
	String echo(@PathVariable("message") String msg);
}
