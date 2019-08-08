package com.stt.service.user.api;

import com.github.pagehelper.PageInfo;
import com.stt.myshop.commons.domain.TbUser;

import java.util.List;

public interface TbUserService {

    List<TbUser> selectAll();

	PageInfo<TbUser> page(int pageNum, int pageSize);
}