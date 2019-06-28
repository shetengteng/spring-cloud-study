package com.stt.spring.boot.thymeleaf.entity;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Administrator on 2019/6/26.
 */
@Data
@Builder
public class PersonBean {

	private String name;
	private Integer age;

}
