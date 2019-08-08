package com.stt.rocketmq.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MySink {
    @Input("input1")
    SubscribableChannel input1();
//
//    @Input("input2")
//    SubscribableChannel input2();
//
//    @Input("input3")
//    SubscribableChannel input3();
//
//    @Input("input4")
//    SubscribableChannel input4();
}