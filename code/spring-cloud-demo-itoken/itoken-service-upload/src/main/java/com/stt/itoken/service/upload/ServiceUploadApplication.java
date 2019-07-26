package com.stt.itoken.service.upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceUploadApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceUploadApplication.class,args);
	}
}
