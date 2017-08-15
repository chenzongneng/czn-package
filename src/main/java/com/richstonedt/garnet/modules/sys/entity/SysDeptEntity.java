/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.entity;

import java.io.Serializable;
import java.util.List;


/**
 * 部门管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 * @since garnet-core-be-fe 1.0.0
 */
public class SysDeptEntity implements Serializable {

	/**
	 * The constant serialVersionUID.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Dept id. 部门ID
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Long deptId;

	/**
	 * The Parent id. 上级部门ID，一级部门为0
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Long parentId;

	/**
	 * The Name. 部门名称
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String name;

	/**
	 * The Parent name. 上级部门名称
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String parentName;

	/**
	 * The Order num. 排序
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Integer orderNum;

	/**
	 * ztree属性
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Boolean open;

	/**
	 * The List.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private List<?> list;


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
	 * Gets dept id.
	 *
	 * @return the dept id
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Long getDeptId() {
		return deptId;
	}

	/**
	 * 设置：上级部门ID，一级部门为0
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取：上级部门ID，一级部门为0
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * 设置：部门名称
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取：部门名称
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置：排序
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * 获取：排序
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Integer getOrderNum() {
		return orderNum;
	}

	/**
	 * Gets parent name.
	 *
	 * @return the parent name
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * Sets parent name.
	 *
	 * @param parentName the parent name
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * Gets open.
	 *
	 * @return the open
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Boolean getOpen() {
		return open;
	}

	/**
	 * Sets open.
	 *
	 * @param open the open
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setOpen(Boolean open) {
		this.open = open;
	}

	/**
	 * Gets list.
	 *
	 * @return the list
	 * @since garnet-core-be-fe 1.0.0
	 */
	public List<?> getList() {
		return list;
	}

	/**
	 * Sets list.
	 *
	 * @param list the list
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setList(List<?> list) {
		this.list = list;
	}
}
