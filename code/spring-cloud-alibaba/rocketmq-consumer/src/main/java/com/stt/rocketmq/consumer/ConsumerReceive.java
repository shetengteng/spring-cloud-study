package com.stt.rocketmq.consumer;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerReceive {

    @StreamListener("input") // 读取bindings的name是input的配置项
    public void receiveInput(String message) {
        System.out.println("Receive input: " + message);
    }

    @StreamListener("input1")
    public void receiveInput1(String receiveMsg) {
        System.out.println("input1 receive: " + receiveMsg);
    }
}