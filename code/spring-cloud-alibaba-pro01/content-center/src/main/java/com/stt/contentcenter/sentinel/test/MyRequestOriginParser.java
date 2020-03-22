package com.stt.contentcenter.sentinel.test;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

// 添加url来源控制
//@Component
public class MyRequestOriginParser implements RequestOriginParser {

	@Override
	public String parseOrigin(HttpServletRequest request) {
		// 从请求参数中获取 origin的参数并返回
		// 如果获取不到origin参数，则抛出异常
		String origin = request.getParameter("the-origin");
		if(StringUtils.isBlank(origin)){
			throw new IllegalArgumentException("origin must be specified");
		}
		return origin;
	}
}