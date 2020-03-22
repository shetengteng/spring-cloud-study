package com.stt.contentcenter.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration 如果添加该注解，需要将该类添加到app启动类的外部
public class UserCenterFeignConfiguration {

	@Bean
	public Logger.Level level(){
		// 让feign打印所有日志的细节
		return Logger.Level.FULL;
	}

}
