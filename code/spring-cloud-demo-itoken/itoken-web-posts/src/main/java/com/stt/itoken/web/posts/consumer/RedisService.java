package com.stt.itoken.web.posts.consumer;

import com.stt.itoken.web.posts.consumer.fallback.RedisServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "itoken-service-redis",fallback = RedisServiceFallback.class)
public interface RedisService {

	@PostMapping("put")
	public String put(@RequestParam("key") String key,
	                  @RequestParam("value") String value,
	                  @RequestParam("seconds") long seconds);

	@GetMapping("get")
	public String get(@RequestParam("key") String key);
}
