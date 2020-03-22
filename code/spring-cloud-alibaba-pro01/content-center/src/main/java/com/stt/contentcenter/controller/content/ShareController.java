package com.stt.contentcenter.controller.content;

import com.github.pagehelper.PageInfo;
import com.stt.contentcenter.auth.CheckAuthorization;
import com.stt.contentcenter.auth.CheckLogin;
import com.stt.contentcenter.domain.dto.share.ShareDTO;
import com.stt.contentcenter.domain.entity.content.Share;
import com.stt.contentcenter.service.content.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shares")
public class ShareController {

	@Autowired
	private ShareService shareService;

	@GetMapping("/{id}")
	@CheckLogin
	public ShareDTO findById(
			@PathVariable Integer id,
			@RequestHeader(value = "X-token",required = false) String token // 接收从前端传递的token
	){
		ShareDTO shareDTO = shareService.findByIdFromFeignAndToken(id,token);
		return shareDTO;
	}

	@GetMapping("/v2/{id}")
	@CheckAuthorization("admin")
	public ShareDTO findById2(
			@PathVariable Integer id
	){
		ShareDTO shareDTO = shareService.findByIdFromFeign(id);
		return shareDTO;
	}


	@GetMapping("/q")
	public PageInfo<Share> q(
			@RequestParam(required = false) String title,
			@RequestParam(required = false,defaultValue = "1") Integer pageNo,
			@RequestParam(required = false,defaultValue = "10") Integer pageSize
	){
		if(pageSize > 100){
			pageSize = 100;
		}
		return this.shareService.q(title,pageNo,pageSize);
	}

}
