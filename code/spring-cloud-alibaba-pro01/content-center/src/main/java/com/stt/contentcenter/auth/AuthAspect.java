package com.stt.contentcenter.auth;

import com.stt.contentcenter.util.JwtOperator;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class AuthAspect {

	@Autowired
	private JwtOperator jwtOperator;

	@Around("@annotation(com.stt.contentcenter.auth.CheckLogin)")
	public Object checkLogin(ProceedingJoinPoint point) throws Throwable {
		try {
			checkToken();
		} catch (Throwable throwable) {
			throw new SecurityException("token 不合法");
		}
		return point.proceed();
	}

	public void checkToken(){
		// 1.从header里面获取token
		// 静态方法获取request
		HttpServletRequest request = getHttpServletRequest();

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
	}

	private HttpServletRequest getHttpServletRequest(){
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttributes;
		HttpServletRequest request = attributes.getRequest();
		return request;
	}


	@Around("@annotation(com.stt.contentcenter.auth.CheckAuthorization)")
	public Object checkAuth(ProceedingJoinPoint point) throws Throwable {
		try{
			checkToken();
			HttpServletRequest request = getHttpServletRequest();
			// 验证角色是否匹配
			String role = request.getAttribute("role").toString();
			// 和注解中的value进行比对
			MethodSignature signature = (MethodSignature) point.getSignature();
			Method method = signature.getMethod();
			CheckAuthorization annotation = method.getAnnotation(CheckAuthorization.class);
			if(!annotation.value().equals(role)){
				throw new SecurityException("用户无权访问");
			}
		}catch (Throwable throwable){
			throw new SecurityException("用户无权访问:",throwable);
		}
		return point.proceed();
	}

}
