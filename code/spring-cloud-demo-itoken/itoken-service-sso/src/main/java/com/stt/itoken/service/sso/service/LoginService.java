package com.stt.itoken.service.sso.service;

import com.stt.itoken.common.domain.TbSysUser;
import com.stt.itoken.common.utils.MapperUtils;
import com.stt.itoken.service.sso.consumer.RedisService;
import com.stt.itoken.service.sso.mapper.TbSysUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Objects;

@Slf4j
@Service
public class LoginService {
//	private static final Logger log = LoggerFactory.getLogger(LoginService.class);

	@Autowired
	private RedisService redisService;

	@Autowired
	private TbSysUserMapper userMapper;

	public TbSysUser login(String loginCode,String plantPassword) throws Exception {

		String json= redisService.get(loginCode);

		if(json == null){
			Example example = new Example(TbSysUser.class);
			example.createCriteria().andEqualTo("loginCode", loginCode);

			TbSysUser user = userMapper.selectOneByExample(example);
			if(Objects.isNull(user)){
				return null;
			}
			if(Objects.equals(DigestUtils.md5DigestAsHex(plantPassword.getBytes()),user.getPassword())){
				redisService.put(loginCode,MapperUtils.obj2json(user),3600);
				return user;
			}
		}

		try {
			return MapperUtils.json2pojo(json, TbSysUser.class);
		} catch (Exception e) {
			// 触发了熔断
			log.error("loginCode:{} error:{}",loginCode,e);
		}
		return null;
	}
}
