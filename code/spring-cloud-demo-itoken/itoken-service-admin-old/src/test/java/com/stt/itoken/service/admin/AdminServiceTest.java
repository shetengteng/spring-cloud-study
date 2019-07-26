package com.stt.itoken.service.admin;

import com.stt.itoken.common.domain.TbSysUser;
import com.stt.itoken.service.admin.service.AdminService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2019/7/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceAdminApplication.class)
// 表示读取prod的配置
@ActiveProfiles(value = "prod")
//@Transactional
//@Rollback
public class AdminServiceTest {


	@Autowired
	private AdminService adminService;

	@Test
	public void registry(){
		TbSysUser user = new TbSysUser();
		user.setUserCode(UUID.randomUUID().toString());
		user.setLoginCode("stt@qq.com");
		user.setUserName("stt");
		user.setPassword("123456");
		user.setUserType("0");
		user.setMgrType("1");
		user.setStatus("0");
		user.setCreateDate(new Date());
		user.setCreateBy(user.getUserCode());
		user.setUpdateDate(new Date());
		user.setUpdateBy(user.getUserCode());
		user.setCorpCode("0");
		user.setCorpName("itoken");
		adminService.register(user);
	}

	@Test
	public void login(){
		TbSysUser stt = adminService.login("stt@qq.com", "123456");
		Assert.assertNotNull(stt);
	}

}
