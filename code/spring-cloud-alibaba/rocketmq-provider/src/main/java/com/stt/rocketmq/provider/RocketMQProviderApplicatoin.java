package com.stt.rocketmq.provider;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.MessageConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableBinding({ Source.class ,MySource.class}) // source 表示发送消息，sink表示接收消息
public class RocketMQProviderApplicatoin implements CommandLineRunner{
	public static void main(String[] args) {
		SpringApplication.run(RocketMQProviderApplicatoin.class,args);
	}

	@Autowired
	private MessageChannel output; // 获取name为output的binding

	@Autowired
	private MessageChannel output1;

	@Override
	public void run(String... args) throws Exception {
//		Map<String, Object> headers = new HashMap<>();
//		headers.put(MessageConst.PROPERTY_TAGS, "tagStr");
//		Message msg = MessageBuilder.createMessage("hello rocketMQ7", new MessageHeaders(headers));
//		output.send(msg);
//		 直接发送
//		output.send(MessageBuilder.withPayload("hello rocketMQ4").build());
//
//		DefaultMQProducer producer = new DefaultMQProducer("producer_group");
//		producer.setNamesrvAddr("192.168.119.132:9876");
//		producer.start();
//
//		Message msg = new Message("test-topic", "tagStr", "message from rocketmq producer".getBytes());
//		producer.send(msg);

		send("hello-test1");

	}

	public void send(String msg) throws Exception {
		output1.send(MessageBuilder.withPayload(msg).build());
	}
}
