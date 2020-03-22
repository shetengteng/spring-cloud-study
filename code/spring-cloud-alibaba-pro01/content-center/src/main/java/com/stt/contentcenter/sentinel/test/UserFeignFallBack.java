package com.stt.contentcenter.sentinel.test;

import com.stt.contentcenter.domain.dto.user.UserDTO;
import com.stt.contentcenter.feign.client.UserCenterFeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class UserFeignFallBack implements UserCenterFeignClient{
	@Override
	public UserDTO findById(Integer id) {
		UserDTO userDTO = new UserDTO();
		userDTO.setWxNickname("流控中");
		return userDTO;
	}
}
