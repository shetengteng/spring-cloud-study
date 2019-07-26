package com.stt.itoken.web.admin.controller;

import com.stt.itoken.common.dto.BaseResult;
import com.stt.itoken.web.admin.feign.AdminFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Administrator on 2019/7/15.
 */
@Controller
public class AdminController {

	@Autowired
	private AdminFeignService adminFeignService;

	// 跳转到index
	@GetMapping(value = {"","login"})
	public String login(){
		BaseResult login = adminFeignService.login("stt@qq.com", "123456");
		System.out.println(login);
		return "index";
	}

}
