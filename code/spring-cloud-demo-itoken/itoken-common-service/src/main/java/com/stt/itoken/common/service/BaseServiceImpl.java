package com.stt.itoken.common.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stt.itoken.common.domain.BaseDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.TkMapper;

import java.util.Date;


@Service
public class BaseServiceImpl<T extends BaseDomain,D extends TkMapper<T>> implements BaseService<T> {

	@Autowired
	private D dao;

	@Override
	@Transactional
	public int insert(T t,String createBy) {
		t.setCreateBy(createBy);
		t.setCreateDate(new Date());
		return dao.insert(t);
	}

	@Override
	@Transactional
	public int delete(T t) {
		return dao.delete(t);
	}

	@Override
	@Transactional
	public int update(T t,String updateBy) {
		t.setUpdateBy(updateBy);
		t.setUpdateDate(new Date());
		return dao.updateByPrimaryKey(t);
	}

	@Override
	public T selectOne(T t) {
		return dao.selectOne(t);
	}

	@Override
	public int count(T t) {
		return dao.selectCount(t);
	}

	@Override
	public PageInfo<T> page(int pageNum, int pageSize,T t) {
		PageHelper.startPage(pageNum,pageSize);
		return new PageInfo<>(dao.select(t));
	}
}
