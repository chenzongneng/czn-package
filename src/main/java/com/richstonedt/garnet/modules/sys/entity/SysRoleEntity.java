/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.entity;


import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:27:38
 * @since garnet-core-be-fe 1.0.0
 */
public class SysRoleEntity implements Serializable {

	/**
	 * The constant serialVersionUID.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色ID
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Long roleId;

	/**
	 * 角色名称
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@NotBlank(message="角色名称不能为空")
	private String roleName;

	/**
	 * 备注
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String remark;

	/**
	 * 部门ID
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@NotNull(message="部门不能为空")
	private Long deptId;

	/**
	 * 部门名称
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String deptName;

	/**
	 * The Menu id list.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private List<Long> menuIdList;

	/**
	 * The Dept id list.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private List<Long> deptIdList;
	
	/**
	 * 创建时间
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Date createTime;

	/**
	 * 设置：
	 *
	 * @param roleId the roleId
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * 获取：
	 *
	 * @return Long
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Long getRoleId() {
		return roleId;
	}
	
	/**
	 * 设置：角色名称
	 *
	 * @param roleName 角色名称
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * 获取：角色名称
	 *
	 * @return String
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getRoleName() {
		return roleName;
	}
	
	/**
	 * 设置：备注
	 *
	 * @param remark 备注
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：备注
	 *
	 * @return String
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * Gets create time.
	 *
	 * @return the create time
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Sets create time.
	 *
	 * @param createTime the create time
	 *  @since garnet-core-be-fe 1.0.0
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Gets menu id list.
	 *
	 * @return the menu id list
	 * @since garnet-core-be-fe 1.0.0
	 */
	public List<Long> getMenuIdList() {
		return menuIdList;
	}

	/**
	 * Sets menu id list.
	 *
	 * @param menuIdList the menu id list
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setMenuIdList(List<Long> menuIdList) {
		this.menuIdList = menuIdList;
	}

	/**
	 * Gets dept id.
	 *
	 * @return the dept id
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Long getDeptId() {
		return deptId;
	}

	/**
	 * Sets dept id.
	 *
	 * @param deptId the dept id
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	/**
	 * Gets dept name.
	 *
	 * @return the dept name
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * Sets dept name.
	 *
	 * @param deptName the dept name
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * Gets dept id list.
	 *
	 * @return the dept id list
	 * @since garnet-core-be-fe 1.0.0
	 */
	public List<Long> getDeptIdList() {
		return deptIdList;
	}

	/**
	 * Sets dept id list.
	 *
	 * @param deptIdList the dept id list
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
}
