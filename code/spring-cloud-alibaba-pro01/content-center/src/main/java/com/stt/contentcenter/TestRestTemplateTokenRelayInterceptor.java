package com.stt.contentcenter;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class TestRestTemplateTokenRelayInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution execution) throws IOException {

		// 1 获取到token
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttributes;
		HttpServletRequest request = attributes.getRequest();
		// 前端在header中放入token
		String token = request.getHeader("X-Token");
		if(StringUtils.isNotBlank(token)){
			// 2 传递token
			HttpHeaders headers = httpRequest.getHeaders();
			headers.add("X-Token",token);
		}

		// 保证继续执行请求
		return execution.execute(httpRequest,bytes);
	}
}
