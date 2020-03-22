package com.stt.contentcenter.controller.user;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.stt.contentcenter.auth.CheckLogin;
import com.stt.contentcenter.domain.entity.user.User;
import com.stt.contentcenter.domain.entity.user.dto.user.JwtTokenRespDTO;
import com.stt.contentcenter.domain.entity.user.dto.user.LoginRespDTO;
import com.stt.contentcenter.domain.entity.user.dto.user.UserLoginDTO;
import com.stt.contentcenter.domain.entity.user.dto.user.UserRespDTO;
import com.stt.contentcenter.service.user.UserService;
import com.stt.contentcenter.util.JwtOperator;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/{id}")
	@CheckLogin
	public User findById(@PathVariable Integer id){
		return userService.findById(id);
	}

	@Autowired
	private WxMaService wxMaService;

	@Autowired
	private JwtOperator jwtOperator;

	@PostMapping("/login")
	public LoginRespDTO login(@RequestBody UserLoginDTO loginDTO) throws WxErrorException {
		// 用code请求微信API验证小程序是否登录，引入小程序api工具包
		// 微信小程序服务端校验是否已登录
		WxMaJscode2SessionResult sessionInfo =
				wxMaService.getUserService().getSessionInfo(loginDTO.getCode());
		// 用户在微信的唯一标识
		String openid = sessionInfo.getOpenid();
		// 看用户是否注册，如果没有注册则插入，如果已经注册则给token
		User user = userService.login(loginDTO, openid);
		// 创建token
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("id",user.getId());
		userInfo.put("wxNickname",user.getWxNickname());
		userInfo.put("role",user.getRoles());

		String token = jwtOperator.generateToken(userInfo);
		log.info("生成的token:{} ，用户登录成功{}",loginDTO.getWxNickname(),token,jwtOperator.getExpirationTime());
		// 构建响应
		return LoginRespDTO.builder()
				.user(
						UserRespDTO.builder()
								.id(user.getId())
								.avatarUrl(user.getAvatarUrl())
								.bonus(user.getBonus())
								.wxNickname(user.getWxNickname())
								.build()
				)
				.token(
						JwtTokenRespDTO.builder()
								.expirationTime(jwtOperator.getExpirationTime().getTime())
								.token(token)
								.build()
				)
				.build();
	}
}
