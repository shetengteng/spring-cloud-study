package com.stt.common.web.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 给前端页面传递常量
 */
public class ConstantsInterceptor implements HandlerInterceptor{

	private static final String HOST_CDN = "http://192.168.119.132:81";
	private static final String TEMPLATE_ADMIN_LTE = "/adminlte/v2.4.3";


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
		if(modelAndView != null){
			// 增加后的效果是在url始终带着
			modelAndView.addObject("adminlte",HOST_CDN+TEMPLATE_ADMIN_LTE);
		}
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

	}

}
