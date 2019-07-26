package com.stt.itoken.web.admin.config;

import com.stt.itoken.web.admin.interceptor.WebAdminInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by Administrator on 2019/7/21.
 */
@Configuration
public class WebAdminInterceptorConfig implements WebMvcConfigurer {

	/**
	 * 由于WebAdminInterceptor 需要使用redisService，需要放在容器中
	 * @return
	 */
	@Bean
	public WebAdminInterceptor webAdminInterceptor(){
		return new WebAdminInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(webAdminInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/static");
	}
}
