/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.entity;


import java.io.Serializable;
import java.util.List;

/**
 * 菜单管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:26:39
 * @since garnet-core-be-fe 1.0.0
 */
public class SysMenuEntity implements Serializable {

	/**
	 * The constant serialVersionUID.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 菜单ID
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Long menuId;

	/**
	 * 父菜单ID，一级菜单为0
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Long parentId;
	
	/**
	 * 父菜单名称
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String parentName;

	/**
	 * 菜单名称
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String name;

	/**
	 * 菜单URL
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String url;

	/**
	 * 授权(多个用逗号分隔，如：user:list,user:create)
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String perms;

	/**
	 * 类型     0：目录   1：菜单   2：按钮
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private Integer type;

	/**
	 * 菜单图标
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private String icon;

	/**
	 * 排序
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
	 * Sets menu id.
	 *
	 * @param menuId the menu id
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	/**
	 * Gets menu id.
	 *
	 * @return the menu id
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Long getMenuId() {
		return menuId;
	}
	
	/**
	 * 设置：父菜单ID，一级菜单为0
	 * @param parentId 父菜单ID，一级菜单为0
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获取：父菜单ID，一级菜单为0
	 *
	 * @return Long
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Long getParentId() {
		return parentId;
	}
	
	/**
	 * 设置：菜单名称
	 *
	 * @param name 菜单名称
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取：菜单名称
	 *
	 * @return String
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置：菜单URL
	 *
	 * @param url 菜单URL
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取：菜单URL
	 * @return String
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Gets perms.
	 *
	 * @return the perms
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getPerms() {
		return perms;
	}

	/**
	 * Sets perms.
	 *
	 * @param perms the perms
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setPerms(String perms) {
		this.perms = perms;
	}

	/**
	 * Gets type.
	 *
	 * @return the type
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * Sets type.
	 *
	 * @param type the type
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 设置：菜单图标
	 *
	 * @param icon 菜单图标
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * 获取：菜单图标
	 *
	 * @return String
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getIcon() {
		return icon;
	}
	
	/**
	 * 设置：排序
	 *
	 * @param orderNum 排序
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * 获取：排序
	 *
	 * @return Integer
	 * @since garnet-core-be-fe 1.0.0
	 */
	public Integer getOrderNum() {
		return orderNum;
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
}
