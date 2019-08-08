package com.stt.service.user.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

// todo 注意使用pageHelper会自动依赖mybatis依赖，需要配置数据源，而消费者不与数据库打交道，需要排除配置
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MyShopServiceUserConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyShopServiceUserConsumerApplication.class,args);
	}
}
