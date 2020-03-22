package com.stt.contentcenter.sentinel.test;

import com.stt.contentcenter.domain.dto.user.UserDTO;
import com.stt.contentcenter.feign.client.UserCenterFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserFeignFallBackFactory implements FallbackFactory<UserCenterFeignClient>{

	@Override
	public UserCenterFeignClient create(Throwable throwable) {
		return new UserCenterFeignClient() {
			@Override
			public UserDTO findById(Integer id) {
				log.warn("远程限流或降级", throwable);
				UserDTO userDTO = new UserDTO();
				userDTO.setWxNickname("限流中");
				return userDTO;
			}
		};
	}
}
