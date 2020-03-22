package com.stt.contentcenter.feign.client;

import com.stt.contentcenter.configuration.UserCenterFeignConfiguration;
import com.stt.contentcenter.domain.dto.user.UserDTO;
import com.stt.contentcenter.sentinel.test.UserFeignFallBack;
import com.stt.contentcenter.sentinel.test.UserFeignFallBackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
//@FeignClient(name="user-center",configuration = UserCenterFeignConfiguration.class)
@FeignClient(
		name="user-center",
// fallback拿不到异常，fallback与fallbackFactory只能存在一个
//		fallback = UserFeignFallBack.class
		fallbackFactory = UserFeignFallBackFactory.class
)
public interface UserCenterFeignHasTokenClient {

	/**
	 * http://user-center/users/{id}
	 * @param id
	 * @return
	 */
	@GetMapping("/users/{id}")
	UserDTO findById(
			@PathVariable(value="id") Integer id,
			@RequestHeader("X-token") String token
	);

}
