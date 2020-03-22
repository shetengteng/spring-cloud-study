package com.stt.contentcenter.rocketmq;

import com.stt.contentcenter.dao.content.RocketmqTransactionLogMapper;
import com.stt.contentcenter.domain.dto.share.ShareAuditDTO;
import com.stt.contentcenter.domain.entity.content.RocketmqTransactionLog;
import com.stt.contentcenter.service.content.ShareService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

//@RocketMQTransactionListener(txProducerGroup = "tx-add-bonus-group")
@Slf4j
public class AddBonusTransactionListener implements RocketMQLocalTransactionListener{

	@Autowired
	private ShareService shareService;

	@Autowired
	private RocketmqTransactionLogMapper rocketmqTransactionLogMapper;

	// 本地事务
	@Override
	public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object arg) {
		// message 是消息体，arg是auditDTO
		MessageHeaders headers = message.getHeaders();
		String transactionId = (String)headers.get(RocketMQHeaders.TRANSACTION_ID);
		Integer sharedId = Integer.valueOf((String)headers.get("shared_id"));

		try{
			shareService.aduitByIdWithRocketMqLog(sharedId,(ShareAuditDTO) arg,transactionId);
			// 为什么要进行回查，在此处挂了，那么commit就没有返回，但本地事务已经提交了
			return RocketMQLocalTransactionState.COMMIT;
		}catch (Exception e){
			e.printStackTrace();
			return RocketMQLocalTransactionState.ROLLBACK;
		}
	}

	// 本地事务的检查接口 第4步二次确认
	@Override
	public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
		// 检查本地事务的状态，使用rocket的日志表
		MessageHeaders headers = message.getHeaders();
		String transactionId = (String)headers.get(RocketMQHeaders.TRANSACTION_ID);

		// select * from xxx where transaction_id = xxx
		RocketmqTransactionLog transactionLog = rocketmqTransactionLogMapper.selectOne(
				RocketmqTransactionLog.builder()
						.transactionId(transactionId)
						.build()
		);

		if(transactionLog != null){
			return RocketMQLocalTransactionState.COMMIT;
		}
		return RocketMQLocalTransactionState.ROLLBACK;
	}
}
