package com.stt.contentcenter.domain.entity.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRespDTO {
	private JwtTokenRespDTO token;
	private UserRespDTO user;
}
