package com.stt.contentcenter.configuration;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

//@Configuration 如果添加该注解，需要将该类添加到app启动类的外部
public class UserCenterFeignTokenConfiguration {

	@Bean
	public Logger.Level level(){
		// 让feign打印所有日志的细节
		return Logger.Level.FULL;
	}
//
//	@Bean
//	public RequestInterceptor requestInterceptor(){
//
//	}

}
