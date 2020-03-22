package com.stt.contentcenter.auth;

import com.stt.contentcenter.util.JwtOperator;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class CheckLoginAspect {

	@Autowired
	private JwtOperator jwtOperator;

	@Around("@annotation(com.stt.contentcenter.auth.CheckLogin)")
	public Object checkLogin(ProceedingJoinPoint point) throws Throwable {
		try {
			// 1.从header里面获取token
			// 静态方法获取request
			RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
			ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttributes;
			HttpServletRequest request = attributes.getRequest();
			// 前端在header中放入token
			String token = request.getHeader("X-Token");
			// 2.校验token是否合法&过期，如果不合法，直接抛弃异常
			Boolean isValid = jwtOperator.validateToken(token);
			if(!isValid){
				throw new SecurityException("token 不合法");
			}
			// 3.如果校验成功，则将用户信息设置到request的attribute中
			Claims claims = jwtOperator.getClaimsFromToken(token);
			request.setAttribute("id",claims.get("id"));
			request.setAttribute("wxNickname",claims.get("wxNickname"));
			request.setAttribute("role",claims.get("role"));

			return point.proceed();
		} catch (Throwable throwable) {
			throw new SecurityException("token 不合法");
		}

	}
}
