package com.stt.itoken.service.admin;

import com.stt.itoken.service.admin.service.AdminService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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



}
