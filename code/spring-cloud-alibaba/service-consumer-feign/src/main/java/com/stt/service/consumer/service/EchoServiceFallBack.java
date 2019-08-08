package com.stt.service.consumer.service;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/8/6.
 */
@Component
public class EchoServiceFallBack implements EchoService {
	@Override
	public String echo(String msg) {
		return "echo fall back";
	}
}
