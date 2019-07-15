package com.stt.itoken.common.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Administrator on 2019/7/15.
 */

@Getter
@AllArgsConstructor
public enum HttpStatusConstant {

	BAD_GATEWAY(502,"网关错误");

	private int status;
	private String content;

}
