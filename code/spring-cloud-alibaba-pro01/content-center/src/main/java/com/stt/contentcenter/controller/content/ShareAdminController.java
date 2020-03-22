package com.stt.contentcenter.controller.content;

import com.stt.contentcenter.auth.CheckAuthorization;
import com.stt.contentcenter.domain.dto.share.ShareAuditDTO;
import com.stt.contentcenter.domain.entity.content.Share;
import com.stt.contentcenter.service.content.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shares")
public class ShareAdminController {

	@Autowired
	ShareService shareService;

	@PutMapping("/audit/{id}")
	@CheckAuthorization("admin")
	public Share auditById(
			@PathVariable Integer id,
			@RequestBody ShareAuditDTO shareAuditDTO){
		return shareService.auditById3(id,shareAuditDTO);
	}

}