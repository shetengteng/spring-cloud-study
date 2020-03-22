package com.stt.contentcenter.domain.dto.share;

import com.stt.contentcenter.domain.enums.AuditStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShareAuditDTO {

	// 审核状态
	private AuditStatusEnum auditStatusEnum;
	// 原因
	private String reason;

}
