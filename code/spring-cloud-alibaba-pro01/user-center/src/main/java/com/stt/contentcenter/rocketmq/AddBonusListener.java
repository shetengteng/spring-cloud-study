package com.stt.contentcenter.rocketmq;

import com.stt.contentcenter.dao.user.BonusEventLogMapper;
import com.stt.contentcenter.dao.user.UserMapper;
import com.stt.contentcenter.domain.entity.user.BonusEventLog;
import com.stt.contentcenter.domain.entity.user.User;
import com.stt.contentcenter.domain.entity.user.dto.msg.UserAddBonusMsgDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

// 接口的泛型是消息体，消费者的group写在代码中
@Slf4j
//@Service
//@RocketMQMessageListener(topic = "add-bonus",consumerGroup = "consumer-group")
public class AddBonusListener implements RocketMQListener<UserAddBonusMsgDTO> {

	@Autowired
	UserMapper userMapper;

	@Autowired
	BonusEventLogMapper bonusEventLogMapper;

	@Override
	public void onMessage(UserAddBonusMsgDTO userAddBonusMsgDTO) {
		// 收到消息的时候，执行的业务
		// 1.为用户添加积分
		Integer userId = userAddBonusMsgDTO.getUserId();

		User user = userMapper.selectByPrimaryKey(userId);

		user.setBonus(user.getBonus()+userAddBonusMsgDTO.getBonus());

		userMapper.updateByPrimaryKeySelective(user);

		// 2.记录日志到bonus_event_log表中
		this.bonusEventLogMapper.insert(
				BonusEventLog.builder()
						.userId(userId)
						.value(userAddBonusMsgDTO.getBonus())
						.event("contribute")
						.createTime(new Date())
						.description("加积分")
						.build()
		);

		log.info("积分添加完毕...");
	}
}
