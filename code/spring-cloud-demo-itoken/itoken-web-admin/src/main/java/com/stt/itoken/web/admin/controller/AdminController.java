package com.stt.itoken.web.admin.controller;

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

	@GetMapping({"","/index"})
	public String index(){
		return "index";
	}

}
