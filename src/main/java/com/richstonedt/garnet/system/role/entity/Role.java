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
 * <b><code>Role</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/8/21 15:20
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 1.0.0
 */
public class Role implements Serializable {

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
	private Integer id;

    /**
     * 租户ID
     *
     * @since garnet-core-be-fe 1.0.0
     */
	private Integer tenantId;

	/**
	 * The Dept name.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String deptName;

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
	 * 角色名称
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String name;

	/**
	 * 备注
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String remark;

	/**
	 * 创建时间
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Date createTime;

	/**
	 * Return the Id
	 *
	 * @return property value of id
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Set the Id
	 *
	 * @param id value to be assigned to property id
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Return the Name
	 *
	 * @return property value of name
	 * @since  garnet-core-be-fe 1.0.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the Name
	 *
	 * @param name value to be assigned to property name
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Return the TenantId
	 *
	 * @return property value of tenantId
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Integer getTenantId() {
		return tenantId;
	}

	/**
	 * Set the TenantId
	 *
	 * @param tenantId value to be assigned to property tenantId
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
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
     * to String
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
