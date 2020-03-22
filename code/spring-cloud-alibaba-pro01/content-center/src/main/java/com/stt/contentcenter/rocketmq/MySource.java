package com.stt.contentcenter.rocketmq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface MySource {

	@Output("my-output")
	MessageChannel output();

	@Output("tx-add-bonus-output")
	MessageChannel txOutput();

}
