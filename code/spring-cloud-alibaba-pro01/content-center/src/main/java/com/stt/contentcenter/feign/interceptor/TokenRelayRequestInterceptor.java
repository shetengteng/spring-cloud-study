package com.stt.contentcenter.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//@Component
public class TokenRelayRequestInterceptor implements RequestInterceptor{

	@Override
	public void apply(RequestTemplate requestTemplate) {
		// 1 获取到token
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttributes;
		HttpServletRequest request = attributes.getRequest();
		// 前端在header中放入token
		String token = request.getHeader("X-Token");
		if(StringUtils.isNotBlank(token)){
			// 2 传递token
			requestTemplate.header("X-Token",token);
		}
	}
}
