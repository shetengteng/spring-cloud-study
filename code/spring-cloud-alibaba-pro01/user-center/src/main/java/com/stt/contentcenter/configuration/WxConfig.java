package com.stt.contentcenter.configuration;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信小程序配置
 */
@Configuration
public class WxConfig {

	@Bean
	public WxMaConfig wxMaConfig(){
		WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
		config.setAppid("wx6797dbdc8e87ed7c");
		config.setSecret("761ed8c352e1279bdd4b8bfe53842e5d");
		return config;
	}

	@Bean
	public WxMaService wxMaService(WxMaConfig wxMaConfig){
		WxMaServiceImpl service = new WxMaServiceImpl();
		service.setWxMaConfig(wxMaConfig);
		return service;
	}
}
