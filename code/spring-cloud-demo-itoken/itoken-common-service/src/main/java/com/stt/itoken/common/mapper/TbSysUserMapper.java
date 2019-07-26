package com.stt.itoken.common.mapper;

import com.stt.itoken.common.domain.TbSysUser;
import com.stt.itoken.common.utils.RedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import tk.mybatis.mapper.TkMapper;

// 配置二级缓存
@CacheNamespace(implementation = RedisCache.class)
public interface TbSysUserMapper extends TkMapper<TbSysUser> {
}