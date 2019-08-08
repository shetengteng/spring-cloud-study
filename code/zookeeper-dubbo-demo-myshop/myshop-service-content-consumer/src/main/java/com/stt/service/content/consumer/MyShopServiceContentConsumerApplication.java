package com.stt.service.content.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MyShopServiceContentConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyShopServiceContentConsumerApplication.class,args);
	}
}
