package com.stt.rocketmq.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

@SpringBootApplication
@EnableBinding({Sink.class,MySink.class})
public class RocketMQConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(RocketMQConsumerApplication.class,args);
	}
}
