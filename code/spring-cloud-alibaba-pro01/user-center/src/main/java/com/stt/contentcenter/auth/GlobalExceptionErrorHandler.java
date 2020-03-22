package com.stt.contentcenter.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionErrorHandler {


	// 封装一个body返回一个json，以及状态码都是401，在页面显示和在network里面可以保持一致
	@ExceptionHandler(SecurityException.class)
	public ResponseEntity<ErrorBody> error(SecurityException se){
		log.warn("发生SecurityException",se);
		ResponseEntity<ErrorBody> responseEntity = new ResponseEntity<>(
				new ErrorBody("token 非法用户不允许访问",HttpStatus.UNAUTHORIZED.value()),
				HttpStatus.UNAUTHORIZED
		);
		return responseEntity;
	}
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class ErrorBody{
	private String body;
	private int status;
}