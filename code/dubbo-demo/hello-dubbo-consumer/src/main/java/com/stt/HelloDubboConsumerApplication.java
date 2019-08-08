package com.stt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
public class HelloDubboConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloDubboConsumerApplication.class, args);
	}

}
