package com.stt.itoken.common.hystrix;

import com.google.common.collect.Lists;
import com.stt.itoken.common.constants.HttpStatusConstant;
import com.stt.itoken.common.dto.BaseResult;
import com.stt.itoken.common.utils.MapperUtils;
import lombok.extern.slf4j.Slf4j;


/**
 * 通用的熔断方法
 */
public class Fallback {

	public static BaseResult badGateway(){
		return BaseResult.notOk(Lists.newArrayList(BaseResult.Error.fromHttpStatus(HttpStatusConstant.BAD_GATEWAY)));
	}

	public static String badGatewayJson(){
		try {
			return MapperUtils.obj2json(Fallback.badGateway());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
