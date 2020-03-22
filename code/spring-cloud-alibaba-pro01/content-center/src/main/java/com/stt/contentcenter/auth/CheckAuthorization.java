package com.stt.contentcenter.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) // 表示在运行期间可以获取到
public @interface CheckAuthorization {
	String value(); // 指定角色的名称
}
