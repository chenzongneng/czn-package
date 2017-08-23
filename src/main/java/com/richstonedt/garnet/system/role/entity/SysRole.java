/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.role.entity;


import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * <b><code>SysRole</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/8/21 15:20
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 1.0.0
 */
public class SysRole implements Serializable {

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
     * 父角色ID
     *
     * @since garnet-core-be-fe 1.0.0
     */
	private Long parentRoleId;

	/**
	 * 角色名称
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String roleName;

	/**
	 * 备注
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String remark;

	/**
	 * 部门id
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Long deptId;

	/**
	 * 部门名称
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String deptName;

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
     * Return the ParentRoleId
     *
     * @return property value of parentRoleId
     * @since gempile-model 0.1.0
     */
    public Long getParentRoleId() {
        return parentRoleId;
    }

    /**
     * Set the ParentRoleId
     *
     * @param parentRoleId value to be assigned to property parentRoleId
     * @since gempile-model 0.1.0
     */
    public void setParentRoleId(Long parentRoleId) {
        this.parentRoleId = parentRoleId;
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
	 * Return the DeptId
	 *
	 * @return property value of deptId
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Long getDeptId() {
		return deptId;
	}

	/**
	 * Return the DeptName
	 *
	 * @return property value of deptName
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * Set the DeptName
	 *
	 * @param deptName value to be assigned to property deptName
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * Set the DeptId
	 *
	 * @param deptId value to be assigned to property deptId
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
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
     * to String
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
