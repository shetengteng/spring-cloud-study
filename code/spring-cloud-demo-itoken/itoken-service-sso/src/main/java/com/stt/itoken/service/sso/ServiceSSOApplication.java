package com.stt.itoken.service.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
// 服务消费者
@EnableDiscoveryClient
@EnableFeignClients
// 服务提供者
@EnableEurekaClient
@MapperScan(basePackages = "com.stt.itoken.service.sso.mapper")
public class ServiceSSOApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceSSOApplication.class,args);
	}
}
