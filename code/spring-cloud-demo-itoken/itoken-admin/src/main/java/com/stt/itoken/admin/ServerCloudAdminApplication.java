package com.stt.itoken.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Created by Administrator on 2019/7/7.
 */

@SpringBootApplication
@EnableEurekaClient
@EnableAdminServer
public class ServerCloudAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerCloudAdminApplication.class,args);
	}
}
