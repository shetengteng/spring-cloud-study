package com.stt.contentcenter.service.user;

import com.stt.contentcenter.dao.user.BonusEventLogMapper;
import com.stt.contentcenter.dao.user.UserMapper;
import com.stt.contentcenter.domain.entity.user.BonusEventLog;
import com.stt.contentcenter.domain.entity.user.User;
import com.stt.contentcenter.domain.entity.user.dto.msg.UserAddBonusMsgDTO;
import com.stt.contentcenter.domain.entity.user.dto.user.UserLoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {
	private final UserMapper userMapper;

	private final BonusEventLogMapper bonusEventLogMapper;

	public User findById(Integer id){
		// select * from user where id = #{id}
		return userMapper.selectByPrimaryKey(id);
	}

	@Transactional(rollbackFor = Exception.class)
	public void addBonus(UserAddBonusMsgDTO userAddBonusMsgDTO){
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
	}

	public User login(UserLoginDTO userLoginDTO,String openId){
		User user = this.userMapper.selectOne(
				User.builder()
						.wxId(openId)
						.build()
		);
		if (user == null){
			User userToSave = User.builder()
					.wxId(openId)
					.bonus(300) // 初始积分
					.wxNickname(userLoginDTO.getWxNickname())
					.avatarUrl(userLoginDTO.getAvatarUrl())
					.roles("user")
					.createTime(new Date())
					.updateTime(new Date())
					.build();
			this.userMapper.insertSelective(userToSave);
			return userToSave;
		}
		// 已经注册则直接返回
		return user;
	}

}
