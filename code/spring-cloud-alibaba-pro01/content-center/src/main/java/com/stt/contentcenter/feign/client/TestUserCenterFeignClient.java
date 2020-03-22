package com.stt.contentcenter.feign.client;

import com.stt.contentcenter.domain.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(name="user-center")
public interface TestUserCenterFeignClient {

	@GetMapping("/test/q")
	UserDTO query(@SpringQueryMap UserDTO userDTO);

}
