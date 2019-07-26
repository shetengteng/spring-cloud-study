package com.stt.itoken.service.posts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages ={
		"com.stt.itoken.common.mapper",
		"com.stt.itoken.service.posts.mapper"
})
@EnableSwagger2
public class ServicePostsApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServicePostsApplication.class,args);
	}
}
