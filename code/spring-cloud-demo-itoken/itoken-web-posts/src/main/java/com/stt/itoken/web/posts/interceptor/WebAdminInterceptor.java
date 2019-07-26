package com.stt.itoken.web.posts.interceptor;

import com.stt.common.web.constants.WebConstants;
import com.stt.common.web.utils.HttpServletUtils;
import com.stt.itoken.common.domain.TbSysUser;
import com.stt.itoken.common.utils.CookieUtils;
import com.stt.itoken.common.utils.MapperUtils;
import com.stt.itoken.web.posts.consumer.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;


public class WebAdminInterceptor implements HandlerInterceptor {

	@Autowired
	private RedisService redisService;

	@Value("${hosts.sso}")
	private String hosts_sso;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = CookieUtils.getCookieValue(request, WebConstants.SESSION_TOKEN);
		if (StringUtils.isEmpty(token)){
			// 表示没有登录，进行单点登录
			response.sendRedirect(redirectUrl(request));
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();
		TbSysUser user = (TbSysUser) session.getAttribute(WebConstants.SESSION_USER);

		// 已登录状态，在本地session中存在
		if(!Objects.isNull(user)){
			if(modelAndView != null){
				modelAndView.addObject(WebConstants.SESSION_USER,user);
			}
		}else{
			// 未登录状态
			String token = CookieUtils.getCookieValue(request,WebConstants.SESSION_TOKEN);
			if(!StringUtils.isEmpty(token)){
				String loginCode = redisService.get(token);
				if(!StringUtils.isEmpty(loginCode)){
					String json = redisService.get(loginCode);
					if(!StringUtils.isEmpty(json)){
						// 已登录状态
						user = MapperUtils.json2pojo(json,TbSysUser.class);
						if(!Objects.isNull(user)){
							// 放入session中，创建局部会话
							request.getSession().setAttribute(WebConstants.SESSION_USER,user);
							if(modelAndView!=null){
								modelAndView.addObject(WebConstants.SESSION_USER,user);
							}
						}
					}
				}
			}
		}
		// 二次确认是否有user
		if(Objects.isNull(user)){
			response.sendRedirect(redirectUrl(request));
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

	}

	private String redirectUrl(HttpServletRequest request){
		return String.format("%s/login?url=%s", hosts_sso, HttpServletUtils.getFullPath(request));
	}

}