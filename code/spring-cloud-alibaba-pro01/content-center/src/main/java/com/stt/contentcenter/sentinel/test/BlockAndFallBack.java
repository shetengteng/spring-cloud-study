package com.stt.contentcenter.sentinel.test;

import com.alibaba.csp.sentinel.slots.block.BlockException;


import org.springframework.cloud.alibaba.sentinel.rest.SentinelClientHttpResponse;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;

public class BlockAndFallBack {
	public static SentinelClientHttpResponse block(HttpRequest httpRequest,
	                           byte[] bytes,
	                           ClientHttpRequestExecution clientHttpRequestExecution,
	                           BlockException e){
		return new SentinelClientHttpResponse("custom block info");
	}

	public static SentinelClientHttpResponse fallback(HttpRequest httpRequest,
	                              byte[] bytes,
	                              ClientHttpRequestExecution clientHttpRequestExecution,
	                              BlockException e){
		return new SentinelClientHttpResponse("custom fallback info");
	}
}
