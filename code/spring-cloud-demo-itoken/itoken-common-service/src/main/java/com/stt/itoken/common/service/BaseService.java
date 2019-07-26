package com.stt.itoken.common.service;

import com.github.pagehelper.PageInfo;
import com.stt.itoken.common.domain.BaseDomain;

/**
 */
public interface BaseService<T extends BaseDomain> {

	int insert(T t,String createBy);
	int delete(T t);
	int update(T t,String updateBy);
	T selectOne(T t);
	int count(T t);

	PageInfo<T> page(int pageNum,int pageSize,T t);

}