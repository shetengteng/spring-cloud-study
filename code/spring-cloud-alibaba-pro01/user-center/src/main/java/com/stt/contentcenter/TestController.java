package com.stt.contentcenter;

import com.stt.contentcenter.dao.user.UserMapper;
import com.stt.contentcenter.domain.entity.user.User;
import com.stt.contentcenter.util.JwtOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

	// idea对mabatis支持不完善，此处有错误提示
	@Autowired
	private UserMapper userMapper;

	@GetMapping("/test")
	public User testInsert(){
		User user = new User();
		user.setAvatarUrl("xxx");
		user.setBonus(222);
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		// insert into tabeName(所有字段) values()
//		userMapper.insert(user);
		// insert into tableName(不为空的字段) values()
		userMapper.insertSelective(user);
		return user;
	}

	@GetMapping("/test/q")
	public User testQuery(User user){
		return user;
	}

	@Autowired
	private JwtOperator jwtOperator;

	@GetMapping("/test/gentoken")
	public String genToken(){
		// 创建token
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("id",1);
		userInfo.put("wxNickname","stt");
		userInfo.put("role","admin");

		String token = jwtOperator.generateToken(userInfo);
		return token;
	}

}
