package com.stt.test;

import com.stt.myshop.commons.domain.TbUser;
import com.stt.service.user.api.TbUserService;
import com.stt.service.user.provider.MyShopServiceUserProviderApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by Administrator on 2019/7/31.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyShopServiceUserProviderApplication.class)
public class BaseTest {

	@Autowired
	private TbUserService tserService;

	@Test
	public void test01(){
		List<TbUser> tbUsers = tserService.selectAll();
		System.out.println(tbUsers);
	}
}
