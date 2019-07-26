package com.stt.itoken.service.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created by Administrator on 2019/7/14.
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages ={
		"com.stt.itoken.common.mapper",
		"com.stt.itoken.service.admin.mapper"
})
public class ServiceAdminApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceAdminApplication.class,args);
	}
}
