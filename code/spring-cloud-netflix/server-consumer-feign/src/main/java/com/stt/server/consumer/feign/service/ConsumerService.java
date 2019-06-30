package com.stt.server.consumer.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "server-provider",fallback = ConsumerServiceHystrix.class)
public interface ConsumerService {

	// 注意，入参必须有RequestParam,同时value必须有值
	@GetMapping("/hi")
	String hi(@RequestParam(value="msg") String msg);

}
