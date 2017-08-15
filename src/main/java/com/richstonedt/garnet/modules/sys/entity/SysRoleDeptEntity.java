/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.entity;


import java.io.Serializable;

/**
 * 角色与部门对应关系
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017年6月21日 23:28:13
 * @since garnet-core-be-fe 1.0.0
 */
public class SysRoleDeptEntity implements Serializable {

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
	 * 角色ID
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Long roleId;

	/**
	 * 部门ID
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Long deptId;

	/**
	 * 设置：
	 *
	 * @param id
	 * @since garnet-core-be-fe 1.0.0
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
	
	/**
	 * 设置：部门ID
	 *
	 * @param deptId 部门ID
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	/**
	 * 获取：部门ID
	 *
	 * @return Long
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Long getDeptId() {
		return deptId;
	}
	
}
