package com.stt.contentcenter.configuration;

import com.stt.contentcenter.rocketmq.MySink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding({Sink.class, MySink.class})
public class StreamSinkConfig {
}
