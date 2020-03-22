package com.stt.contentcenter.dao.user;

import com.stt.contentcenter.domain.entity.user.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserMapper extends Mapper<User> {
}