package com.stt.itoken.service.admin.controller;

import com.github.pagehelper.PageInfo;
import com.stt.itoken.common.domain.TbSysUser;
import com.stt.itoken.common.dto.BaseResult;
import com.stt.itoken.common.dto.BaseResult.Cursor;
import com.stt.itoken.service.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2019/7/14.
 */

@RestController
@RequestMapping(value = "/v1/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping("/page/{pageNum}/{pageSize}")
	public BaseResult page(
			@PathVariable(required = true) int pageNum,
			@PathVariable(required = true) int pageSize,
			@RequestParam(required = false)TbSysUser user){

		PageInfo<TbSysUser> page = adminService.page(pageNum, pageSize, user);

		return BaseResult.page(
				page.getList(),
				Cursor.builder()
						.limit(page.getPageSize())
						.offset(page.getPageNum())
						.total(page.getTotal())
						.build());
	}

}
