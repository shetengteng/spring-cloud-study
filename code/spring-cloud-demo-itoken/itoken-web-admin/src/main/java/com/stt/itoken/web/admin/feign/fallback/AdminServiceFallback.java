package com.stt.itoken.web.admin.feign.fallback;

import com.google.common.collect.Lists;
import com.stt.itoken.common.constants.HttpStatusConstant;
import com.stt.itoken.common.dto.BaseResult;
import com.stt.itoken.web.admin.feign.AdminFeignService;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2019/7/15.
 */
// 注意必须要加入到容器中，使用Component注解
@Component
public class AdminServiceFallback implements AdminFeignService {
	@Override
	public BaseResult login(String loginCode, String password) {
		return BaseResult.notOk(
				Lists.newArrayList(BaseResult.Error.fromHttpStatus(HttpStatusConstant.BAD_GATEWAY)));
	}
}
