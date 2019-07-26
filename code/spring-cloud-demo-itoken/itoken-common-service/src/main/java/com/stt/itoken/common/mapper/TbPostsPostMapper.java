package com.stt.itoken.common.mapper;

import com.stt.itoken.common.domain.TbPostsPost;
import com.stt.itoken.common.utils.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.TkMapper;

@CacheNamespace(implementation = RedisCache.class)
public interface TbPostsPostMapper extends TkMapper<TbPostsPost> {
}