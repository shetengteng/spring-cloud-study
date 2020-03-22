package com.stt.contentcenter;

import com.stt.contentcenter.sentinel.test.BlockAndFallBack;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.alibaba.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.Collections;

// 扫描mybatis哪些包的接口
@MapperScan("com.stt.contentcenter.dao")
@SpringBootApplication
//@EnableFeignClients(defaultConfiguration = UserCenterFeignConfiguration.class)
@EnableFeignClients
//@EnableBinding(Source.class)
public class ContentCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContentCenterApplication.class, args);
	}


	// 在spring容器中，创建一个对象，类型RestTemplate
	// <bean id="restTemplate" class = "xxx.RestTemplate"
	@Bean
	@LoadBalanced
	@SentinelRestTemplate(
			blockHandler = "block",
			fallback = "fallback",
			blockHandlerClass = BlockAndFallBack.class,
			fallbackClass = BlockAndFallBack.class
	)
	public RestTemplate restTemplate(){
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setInterceptors(
				Collections.singletonList(
						new TestRestTemplateTokenRelayInterceptor()
				)
		);
		return restTemplate;
	}

}
