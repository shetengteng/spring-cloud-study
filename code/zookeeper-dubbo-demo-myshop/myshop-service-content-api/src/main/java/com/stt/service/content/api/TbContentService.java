package com.stt.service.content.api;

import com.github.pagehelper.PageInfo;
import com.stt.myshop.commons.domain.TbContent;

public interface TbContentService {

	PageInfo<TbContent> page(int pageNum,int pageSize);

}
