/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.entity;

import com.richstonedt.garnet.common.validator.group.AddGroup;
import com.richstonedt.garnet.common.validator.group.UpdateGroup;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:28:55
 * @since garnet-core-be-fe 1.0.0
 */
public class SysUserEntity implements Serializable {

	/**
	 * The constant serialVersionUID.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户ID
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Long userId;

	/**
	 * 用户名
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@NotBlank(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String username;

	/**
	 * 密码
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@NotBlank(message="密码不能为空", groups = AddGroup.class)
	private String password;

	/**
	 * 盐
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String salt;

	/**
	 * 邮箱
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@NotBlank(message="邮箱不能为空", groups = {AddGroup.class, UpdateGroup.class})
	@Email(message="邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
	private String email;

	/**
	 * 手机号
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String mobile;

	/**
	 * 状态  0：禁用   1：正常
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Integer status;
	
	/**
	 * 角色ID列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private List<Long> roleIdList;
	
	/**
	 * 创建者ID
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Long createUserId;

	/**
	 * 创建时间
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Date createTime;

	/**
	 * 部门ID
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	//@NotNull(message="部门不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Long deptId;

	/**
	 * 部门名称
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String deptName;

	/**
	 * 角色id
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Long roleId;

	/**
	 * The Admin.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Integer admin;

	/**
	 * Return the IsAdmin
	 *
	 * @return property value of isAdmin
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Integer getAdmin() {
		return admin;
	}

	/**
	 * Set the IsAdmin
	 *
	 * @param isAdmin value to be assigned to property isAdmin
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setAdmin(Integer isAdmin) {
		this.admin = isAdmin;
	}

	/**
	 * 设置：
	 * @param userId  the id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * 获取：
	 *
	 * @return Long
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Long getUserId() {
		return userId;
	}
	
	/**
	 * 设置：用户名
	 *
	 * @param username 用户名
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取：用户名
	 *
	 * @return String
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * 设置：密码
	 *
	 * @param password 密码
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取：密码
	 *
	 * @return String
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * 设置：邮箱
	 *
	 * @param email 邮箱
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取：邮箱
	 *
	 * @return String
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * 设置：手机号
	 *
	 * @param mobile 手机号
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取：手机号
	 *
	 * @return String
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getMobile() {
		return mobile;
	}
	
	/**
	 * 设置：状态  0：禁用   1：正常
	 * @param status 状态  0：禁用   1：正常
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 获取：状态  0：禁用   1：正常
	 *
	 * @return Integer
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Integer getStatus() {
		return status;
	}
	
	/**
	 * 设置：创建时间
	 *
	 * @param createTime 创建时间
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取：创建时间
	 *
	 * @return Date
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Gets role id list.
	 *
	 * @return the role id list
	 * @since garnet-core-be-fe 1.0.0
	 */
	public List<Long> getRoleIdList() {
		return roleIdList;
	}

	/**
	 * Sets role id list.
	 *
	 * @param roleIdList the role id list
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setRoleIdList(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}

	/**
	 * Gets create user id.
	 *
	 * @return the create user id
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Long getCreateUserId() {
		return createUserId;
	}

	/**
	 * Sets create user id.
	 *
	 * @param createUserId the create user id
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * Gets salt.
	 *
	 * @return the salt
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * Sets salt.
	 *
	 * @param salt the salt
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setSalt(String salt) {
		this.salt = salt;
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
	 * Return the RoleId
	 *
	 * @return property value of roleId
	 * @since  garnet-core-be-fe 1.0.0
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * Set the RoleId
	 *
	 * @param roleId value to be assigned to property roleId
	 * @since  garnet-core-be-fe 1.0.0
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
