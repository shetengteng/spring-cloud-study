package com.stt.itoken.web.admin.feign;

import com.stt.itoken.common.dto.BaseResult;
import com.stt.itoken.web.admin.feign.fallback.AdminServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Administrator on 2019/7/15.
 */
@FeignClient(value = "itoken-service-admin",fallback = AdminServiceFallback.class)
public interface AdminFeignService {

	@GetMapping("login")
	public BaseResult login(@RequestParam("loginCode") String loginCode,
	                        @RequestParam("password") String password);

}
