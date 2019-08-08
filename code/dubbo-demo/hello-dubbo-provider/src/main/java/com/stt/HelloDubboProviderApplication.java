package com.stt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import sun.applet.Main;

@SpringBootApplication
@EnableHystrix
public class HelloDubboProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloDubboProviderApplication.class, args);
		Main.main(args);
	}

}
