package com.stt.config;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2019/7/31.
 */
@EnableHystrix
@Configuration
public class HsystrixConfig {}
