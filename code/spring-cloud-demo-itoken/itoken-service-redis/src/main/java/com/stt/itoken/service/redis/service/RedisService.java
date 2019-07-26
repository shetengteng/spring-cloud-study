package com.stt.itoken.service.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2019/7/17.
 */
@Service
public class RedisService {

	@Autowired
	private RedisTemplate redisTemplate;

	public void put(String key,Object value,long seconds){
		redisTemplate.opsForValue().set(key,value,seconds, TimeUnit.SECONDS);
	}

	public Object get(String key){
		return redisTemplate.opsForValue().get(key);
	}

}
