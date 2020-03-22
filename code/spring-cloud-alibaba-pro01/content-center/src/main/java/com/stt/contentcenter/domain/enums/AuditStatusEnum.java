package com.stt.contentcenter.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuditStatusEnum {

	/**
	 * 待审核
	 */
	NOT_YET,
	/**
	 * 审核通过
	 */
	PASS,
	/**
	 * 审核不通过
	 */
	REJECT

}
