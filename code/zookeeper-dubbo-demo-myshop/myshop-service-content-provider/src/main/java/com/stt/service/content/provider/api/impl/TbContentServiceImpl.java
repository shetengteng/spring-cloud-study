package com.stt.service.content.provider.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stt.myshop.commons.domain.TbContent;
import com.stt.myshop.commons.domain.TbUser;
import com.stt.myshop.commons.mapper.TbContentMapper;
import com.stt.service.content.api.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;


@Service(version = "${services.versions.content.v1}")
@Transactional(readOnly = true)
public class TbContentServiceImpl implements TbContentService {

	@Autowired
	private TbContentMapper tbContentMapper;

	@Override
	public PageInfo<TbContent> page(int pageNum, int pageSize) {
		Example example = new Example(TbUser.class);
		PageHelper.startPage(pageNum,pageSize);
		PageInfo<TbContent> pageInfo = new PageInfo<>(tbContentMapper.selectByExample(example));
		return pageInfo;
	}
}
