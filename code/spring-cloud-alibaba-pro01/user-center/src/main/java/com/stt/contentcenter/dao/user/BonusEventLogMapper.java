package com.stt.contentcenter.dao.user;

import com.stt.contentcenter.domain.entity.user.BonusEventLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface BonusEventLogMapper extends Mapper<BonusEventLog> {
}