package com.stt.contentcenter.dao.content;

import com.stt.contentcenter.domain.entity.content.RocketmqTransactionLog;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface RocketmqTransactionLogMapper extends Mapper<RocketmqTransactionLog> {
}