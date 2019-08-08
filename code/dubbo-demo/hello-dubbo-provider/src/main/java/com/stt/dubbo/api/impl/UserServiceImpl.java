package com.stt.dubbo.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.stt.dubbo.demo.service.user.api.UserService;


@Service(version = "${user.service.version}")
public class UserServiceImpl implements UserService{


	@Override
	public String sayHi() {
//		return "hello:"+ RpcContext.getContext().getLocalAddress();
		throw new RuntimeException("error");
	}
}
