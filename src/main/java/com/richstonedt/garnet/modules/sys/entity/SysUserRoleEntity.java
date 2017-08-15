/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.entity;


import java.io.Serializable;

/**
 * 用户与角色对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:28:39
 * @since garnet-core-be-fe 1.0.0
 */
public class SysUserRoleEntity implements Serializable {

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
	 * 用户ID
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Long userId;

	/**
	 * 角色ID
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Long roleId;

	/**
	 * 设置：
	 *
	 * @param id  the id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取：
	 *
	 * @return Long
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * 设置：用户ID
	 *
	 * @param userId 用户ID
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取：用户ID
	 *
	 * @return Long
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Long getUserId() {
		return userId;
	}
	
	/**
	 * 设置：角色ID
	 *
	 * @param roleId 角色ID
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * 获取：角色ID
	 *
	 * @return Long
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Long getRoleId() {
		return roleId;
	}
	
}
