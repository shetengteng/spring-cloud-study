package com.stt.contentcenter.configuration;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Configuration;
import ribbon.configuration.RibbonConfiguration;

/**
 *
 * 配置usercenter的ribbon
 */
@Configuration
@RibbonClient(name = "user-center",configuration = RibbonConfiguration.class)
public class UserCenterRibbonConfiguration {

}