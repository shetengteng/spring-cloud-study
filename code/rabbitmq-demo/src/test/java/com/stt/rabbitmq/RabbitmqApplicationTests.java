package com.stt.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitmqApplication.class)
public class RabbitmqApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Autowired
	private RabbitProvider rabbitProvider;

	@Test
	public void send(){
		for(int i = 0;i<100;i++){
			rabbitProvider.send();
		}
	}

}
