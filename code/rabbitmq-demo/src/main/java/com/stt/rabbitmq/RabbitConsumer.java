package com.stt.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.stt.rabbitmq.RabbitmqConfig.queueName;

/**
 * Created by Administrator on 2019/7/26.
 */
@Component
@RabbitListener(queues = queueName)
public class RabbitConsumer {

	@RabbitHandler
	public void process(String content){
		System.out.println("receive: "+content);
	}

}
