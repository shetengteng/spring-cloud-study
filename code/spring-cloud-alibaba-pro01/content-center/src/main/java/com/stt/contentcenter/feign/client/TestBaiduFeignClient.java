package com.stt.contentcenter.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

// 必须要有name属性
@Component
@FeignClient(name = "baidu",url="http://www.baidu.com")
public interface TestBaiduFeignClient {

	@GetMapping("")
	String index();
}
