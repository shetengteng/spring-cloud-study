package com.stt.itoken.service.admin.service;

import com.stt.itoken.common.domain.TbSysUser;
import com.stt.itoken.service.admin.mapper.TbSysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Objects;


/**
 * Created by Administrator on 2019/7/14.
 */
@Service
public class AdminService {


	@Autowired
	private TbSysUserMapper userMapper;

	/**
	 * 注册
	 * @param user
	 */
	@Transactional(rollbackFor = Throwable.class)
	public void register(TbSysUser user){
		user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
		userMapper.insert(user);
	}

	/**
	 * 登录
	 * @param loginCode 账号
	 * @param plantPassword 明文密码
	 * @return
	 */
	public TbSysUser login(String loginCode,String plantPassword){

		Example example = new Example(TbSysUser.class);
		example.createCriteria().andEqualTo("loginCode", loginCode);

		TbSysUser user = userMapper.selectOneByExample(example);
		if(Objects.isNull(user)){
			return null;
		}
		if(Objects.equals(DigestUtils.md5DigestAsHex(plantPassword.getBytes()),user.getPassword())){
			return user;
		}
		return null;
	}

}
