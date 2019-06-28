package com.stt.spring.boot.mybatis;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stt.spring.boot.mybatis.entity.TbUser;
import com.stt.spring.boot.mybatis.mapper.TbUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by Administrator on 2019/6/26.
 */
@RunWith(SpringRunner.class)
// 这里配置classes 用于读取yml配置文件
@SpringBootTest(classes = SpringBootMybatisApplication.class)
@Transactional
@Rollback
public class MybatisTest {

	/**
	 * 注入数据查询接口
	 */
	@Autowired
	private TbUserMapper tbUserMapper;


	/**
	 * 测试分页查询
	 */
	@Test
	public void testPage() {
		// PageHelper 使用非常简单，只需要设置页码和每页显示笔数即可
		PageHelper.startPage(0, 2);

		// 设置分页查询条件
		Example example = new Example(TbUser.class);
		PageInfo<TbUser> pageInfo = new PageInfo<>(tbUserMapper.selectByExample(example));

		// 获取查询结果
		List<TbUser> tbUsers = pageInfo.getList();
		for (TbUser tbUser : tbUsers) {
			System.out.println(tbUser.getUsername());
		}

	}


	@Test
	public void testOne(){
		TbUser user = new TbUser();
		user.setId(7L);
		TbUser user1 = tbUserMapper.selectOne(user);
		System.out.println(user1);
	}

}
