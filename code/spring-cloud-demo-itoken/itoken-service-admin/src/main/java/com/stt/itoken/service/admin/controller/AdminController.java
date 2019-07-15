package com.stt.itoken.service.admin.controller;

import com.google.common.collect.Lists;
import com.stt.itoken.common.dto.BaseResult;
import com.stt.itoken.service.admin.domain.TbSysUser;
import com.stt.itoken.service.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by Administrator on 2019/7/14.
 */

@RestController
public class AdminController {

	@Autowired
	private AdminService adminService;

	@GetMapping("login")
	public BaseResult login(String loginCode,String password){
		Optional<BaseResult> re = checkLogin(loginCode,password);
		if(re.isPresent()){
			return re.get();
		}

		TbSysUser user = adminService.login(loginCode, password);
		if(!Objects.isNull(user)){
			return BaseResult.ok(user);
		}
		return BaseResult.notOk(
					Lists.newArrayList(
						BaseResult.Error.builder().field("").message("登录失败").build()));
	}

	private Optional<BaseResult> checkLogin(String loginCode, String password) {
		return StringUtils.isEmpty(loginCode) || StringUtils.isEmpty(password) ?
				Optional.ofNullable(BaseResult.notOk(
					Lists.newArrayList(
							new BaseResult.Error("loginCode","用户名或密码错误"),
							new BaseResult.Error("password","用户名或密码错误")
					))) : Optional.empty();
	}

}
