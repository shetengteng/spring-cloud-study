package com.stt.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

	public static final String queueName = "hello-rabbitmq";

	@Bean
	public Queue queue(){
		return new Queue(queueName);
	}

}
