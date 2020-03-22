package com.stt.contentcenter.domain.entity.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserRespDTO {

	private Integer id;
	// 头像地址
	private String avatarUrl;
	// 积分
	private Integer bonus;
	// 昵称
	private String wxNickname;
}
