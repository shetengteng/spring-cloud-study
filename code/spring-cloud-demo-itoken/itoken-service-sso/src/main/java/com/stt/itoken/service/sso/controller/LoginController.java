package com.stt.itoken.service.sso.controller;

import com.stt.itoken.common.domain.TbSysUser;
import com.stt.itoken.common.utils.CookieUtils;
import com.stt.itoken.common.utils.MapperUtils;
import com.stt.itoken.service.sso.consumer.RedisService;
import com.stt.itoken.service.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.UUID;

@Controller
public class LoginController {

	private static final String REDIRECT = "redirect:";
	private static final String MSG = "message";
	private static final String LOGIN = "login";
	private static final String TOKEN = "token";

	@Autowired
	private LoginService loginService;

	@Autowired
	private RedisService redisService;

	/**
	 * 跳转到登录页面,判断是否登录
	 * @return
	 */
	@GetMapping(LOGIN)
	public String login(@RequestParam(required = false) String url,
	                    HttpServletRequest request,
	                    Model model){
		String token = CookieUtils.getCookieValue(request,TOKEN);
		if(!StringUtils.isEmpty(token)){
			String loginCode = redisService.get(token);
			if(!StringUtils.isEmpty(loginCode)){
				String userJson = redisService.get(loginCode);
				if(!StringUtils.isEmpty(userJson)){
					try {
						// 登录成功后返回登录者的信息，如头像，昵称等
						TbSysUser tbSysUser = MapperUtils.json2pojo(userJson, TbSysUser.class);
						if(!Objects.isNull(tbSysUser)){
							model.addAttribute("user",tbSysUser);
							if(!StringUtils.isEmpty(url)){
								return REDIRECT + url;
							}
						}
					}catch (Exception e){
                        e.printStackTrace();
					}
				}
			}
		}

		// 如果url不为空，需要放在隐藏的input框中，下次提交带过来
		if(!StringUtils.isEmpty(url)){
			model.addAttribute("url",url);
		}

		// 通过token判断是否登录过
		return "login.html"; // 返回login.html
	}

	/**
	 * 登录操作，产生一个token放入redis和cookie中，下次查询登录信息带上token查询
	 * @param loginCode
	 * @param password
	 * @param url
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@PostMapping("doLogin")
	public String submitLogin(@RequestParam String loginCode,
	                    @RequestParam String password,
	                    @RequestParam(required = false) String url,
	                    HttpServletRequest request,
	                    HttpServletResponse response,
						RedirectAttributes attributes, // 重定向使用
						Model model // 直接跳转使用
						) throws Exception {

		TbSysUser login = loginService.login(loginCode, password);

		if(Objects.isNull(login)){
//			model.addAttribute(MSG,"用户名或密码错误");
			attributes.addFlashAttribute(MSG,"用户名或密码错误");
			return REDIRECT+LOGIN;
		}

		String token = UUID.randomUUID().toString();
		// 在redis中存储一个 token--> loginCode
		String re =	redisService.put(token,loginCode,3600);
		if("ok".equals(re)){
		    // 在cookie中放置token
			CookieUtils.setCookie(request,response,"token",token,3600);
			if(!StringUtils.isEmpty(url)){
				return REDIRECT + url;
			}
		}else {
			// 熔断
//			model.addAttribute(MSG","服务异常");
			attributes.addFlashAttribute(MSG,"服务异常");
		}

		return REDIRECT+LOGIN;
	}

	/**
	 * 单点注销，将cookie中的token删除
	 * @param request
	 * @return
	 */
	@GetMapping("/logout")
	public String logout(@RequestParam(required = false) String url,
	                     HttpServletRequest request,
	                     HttpServletResponse response,
	                     Model model){

		CookieUtils.deleteCookie(request,response,TOKEN);
		// 应该将redis中的token删除
		return login(url,request,model);
	}

}