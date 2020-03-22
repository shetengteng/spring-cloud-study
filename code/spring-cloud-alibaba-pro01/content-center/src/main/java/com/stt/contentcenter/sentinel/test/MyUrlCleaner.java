package com.stt.contentcenter.sentinel.test;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlCleaner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//@Component
@Slf4j
public class MyUrlCleaner implements UrlCleaner {

	@Override
	public String clean(String originUrl) {
		log.info("originUrl={}",originUrl);
		// 让shares/1与shares/2返回值相同

		String[] split = originUrl.split("/");

		String re = Arrays.stream(split).map(s -> {
			if(NumberUtils.isNumber(s)){
				return "{number}";
			}
			return s;
		}).reduce((a,b) -> a+"/"+b).orElse("");

		return re;
	}
}