package com.stt.itoken.service.redis.controller;

import com.stt.itoken.service.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class RedisController {

	@Autowired
	private RedisService redisService;

	@PostMapping("/put")
	public String put(String key,String value,long seconds){
		redisService.put(key,value,seconds);
		return "ok";
	}

	@GetMapping("/get")
	public String get(String key){
		Object re = redisService.get(key);
		if(Objects.isNull(re)){
			return null;
		}
		return re.toString();
	}

}
