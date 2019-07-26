package com.stt.common.web.config;

import com.stt.common.web.interceptor.ConstantsInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置拦截器
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer{

	/**
	 * 使用@Bean配置拦截器的好处是 该拦截器会注入到ioc容器中
	 * 拦截器可以注入ioc容器对象
	 * @return
	 */
	@Bean
	public ConstantsInterceptor constantsInterceptor(){
		return new ConstantsInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(constantsInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/static/**");
	}
}
