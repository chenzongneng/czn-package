/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 系统日志
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-08 10:40:56
 * @since garnet-core-be-fe 1.0.0
 */
public class SysLogEntity implements Serializable {

	/**
	 * The constant serialVersionUID.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Id.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Long id;

	/**
	 * The Username. 用户名
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String username;

	/**
	 * The Operation.  用户操作
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String operation;

	/**
	 * The Method.  请求方法
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String method;

	/**
	 * The Params. 请求参数
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String params;

	/**
	 * The Time. 执行时长(毫秒)
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Long time;

	/**
	 * The Ip.   IP地址
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String ip;

	/**
	 * The Create date.  创建时间
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Date createDate;

	/**
	 * 设置：
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取：
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置：用户名
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取：用户名
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置：用户操作
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * 获取：用户操作
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * 设置：请求方法
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * 获取：请求方法
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * 设置：请求参数
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setParams(String params) {
		this.params = params;
	}

	/**
	 * 获取：请求参数
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getParams() {
		return params;
	}

	/**
	 * 设置：IP地址
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 获取：IP地址
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 设置：创建时间
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 获取：创建时间
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Gets time.
	 *
	 * @return the time
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Long getTime() {
		return time;
	}

	/**
	 * Sets time.
	 *
	 * @param time the time
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setTime(Long time) {
		this.time = time;
	}
}
