package com.stt.contentcenter.configuration;

import com.stt.contentcenter.rocketmq.MySource;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding({Source.class, MySource.class})
public class StreamSourceConfig {
}
