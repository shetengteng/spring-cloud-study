package com.stt.contentcenter.domain.entity.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JwtTokenRespDTO {
	private String token;
	// 过期时间
	private Long expirationTime;
}
