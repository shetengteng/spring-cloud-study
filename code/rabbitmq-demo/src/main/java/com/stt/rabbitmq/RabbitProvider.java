package com.stt.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.stt.rabbitmq.RabbitmqConfig.queueName;

/**
 * Created by Administrator on 2019/7/26.
 */
@Component
public class RabbitProvider {

	@Autowired
	private AmqpTemplate amqpTemplate;

	public void send(){
		String content = "hello "+new Date();
		System.out.println("send: "+content);
		amqpTemplate.convertAndSend(queueName,content);
	}

}
