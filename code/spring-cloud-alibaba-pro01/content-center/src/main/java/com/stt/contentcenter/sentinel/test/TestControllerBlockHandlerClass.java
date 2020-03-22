package com.stt.contentcenter.sentinel.test;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestControllerBlockHandlerClass {

	// blockHandler对应的方法，需要入参和返回值同调用方法基本一致
	public static String block(String a,BlockException e){
		// 如果被保护的资源被限流或者降级了，则抛出该异常
		log.warn("限流，降级 {}", e);
		return "限流 block";
	}

}
