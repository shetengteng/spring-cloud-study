package com.stt.contentcenter.configuration;

import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;
import ribbon.configuration.RibbonConfiguration;

/**
 *
 *  全局配置
 */
@Configuration
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
public class GlobalRibbonConfiguration {

}