package com.stt.commons.dubbo.config;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Configuration;

@EnableHystrix
@EnableHystrixDashboard
@Configuration
public class HystrixConfig {
}
