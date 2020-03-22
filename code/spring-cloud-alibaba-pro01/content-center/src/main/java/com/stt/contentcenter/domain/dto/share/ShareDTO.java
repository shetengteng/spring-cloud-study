package com.stt.contentcenter.domain.dto.share;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
public class ShareDTO {


	private Integer id;


	private Integer userId;

	/**
	 * 标题
	 */
	private String title;


	private Date createTime;


	private Date updateTime;


	private Boolean isOriginal;

	/**
	 * 作者
	 */
	private String author;

	/**
	 * 封面
	 */
	private String cover;

	/**
	 * 概要信息
	 */
	private String summary;

	/**
	 * 价格（需要的积分）
	 */
	private Integer price;

	private String downloadUrl;

	private Integer buyCount;

	private Boolean showFlag;

	private String auditStatus;

	private String reason;

	/**
	 * 发布人昵称
	 */
	private String wxNickname;
}