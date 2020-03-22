package com.stt.contentcenter.domain.entity.user.dto.msg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddBonusMsgDTO {

	// 给谁加积分
	Integer userId;
	// 加多少积分
	Integer bonus;

}
