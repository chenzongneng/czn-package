/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.entity;


import org.hibernate.validator.constraints.NotBlank;

/**
 * 系统配置信息
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:43:36
 * @since garnet-core-be-fe 1.0.0
 */
public class SysConfigEntity {

	/**
	 * The Id.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Long id;

	/**
	 * The Key.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@NotBlank(message="参数名不能为空")
	private String key;

	/**
	 * The Value.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@NotBlank(message="参数值不能为空")
	private String value;

	/**
	 * The Remark.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String remark;

	/**
	 * Gets id.
	 *
	 * @return the id
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets key.
	 *
	 * @return the key
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets key.
	 *
	 * @param key the key
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Gets value.
	 *
	 * @return the value
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets value.
	 *
	 * @param value the value
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Gets remark.
	 *
	 * @return the remark
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * Sets remark.
	 *
	 * @param remark the remark
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
