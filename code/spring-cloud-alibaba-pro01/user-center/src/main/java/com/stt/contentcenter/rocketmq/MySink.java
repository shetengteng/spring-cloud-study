package com.stt.contentcenter.rocketmq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MySink {

	String MY_INPUT = "my-input";

	@Input(MY_INPUT)
	SubscribableChannel input();

	String MY_TX_INPUT="my-tx-input";

	@Input(MY_TX_INPUT)
	SubscribableChannel txInput();
}
