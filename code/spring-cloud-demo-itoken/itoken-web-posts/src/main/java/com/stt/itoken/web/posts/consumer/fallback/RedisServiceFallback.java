package com.stt.itoken.web.posts.consumer.fallback;

import com.stt.itoken.common.hystrix.Fallback;
import com.stt.itoken.web.posts.consumer.RedisService;
import org.springframework.stereotype.Component;

@Component
public class RedisServiceFallback implements RedisService {
	@Override
	public String put(String key, String value, long seconds) {
		return Fallback.badGatewayJson();
	}

	@Override
	public String get(String key) {
		return Fallback.badGatewayJson();
	}
}
