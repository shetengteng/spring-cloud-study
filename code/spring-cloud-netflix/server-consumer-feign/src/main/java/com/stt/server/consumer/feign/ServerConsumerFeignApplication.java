package com.stt.server.consumer.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient

// 可以使用EnableDiscoveryClient 代替 EnableEurekaClient
// EnableDiscoveryClient 基于spring-cloud-commons ，EnableEurekaClient基于spring-cloud-netflix
// 如果注册中心是Eureka的话，推荐使用EnableEurekaClient
//@EnableDiscoveryClient

@EnableFeignClients
public class ServerConsumerFeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerConsumerFeignApplication.class, args);
	}

}
