/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.service;

import com.richstonedt.garnet.modules.sys.entity.SysMenuEntity;

import java.util.List;
import java.util.Map;


/**
 * 菜单管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:16
 * @since garnet-core-be-fe 1.0.0
 */
public interface SysMenuService {
	
	/**
	 * 根据父菜单，查询子菜单
	 *
	 * @param parentId 父菜单ID
	 * @param menuIdList  用户菜单ID
	 * @since garnet-core-be-fe 1.0.0
	 */
	List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);

	/**
	 * 根据父菜单，查询子菜单
	 *
	 * @param parentId 父菜单ID
	 * @since garnet-core-be-fe 1.0.0
	 */
	List<SysMenuEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	List<SysMenuEntity> queryNotButtonList();
	
	/**
	 * 获取用户菜单列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	List<SysMenuEntity> getUserMenuList(Long userId);
	
	/**
	 * 查询菜单
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	SysMenuEntity queryObject(Long menuId);
	
	/**
	 * 查询菜单列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	List<SysMenuEntity> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存菜单
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	void save(SysMenuEntity menu);
	
	/**
	 * 修改
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	void update(SysMenuEntity menu);
	
	/**
	 * 删除
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	void deleteBatch(Long[] menuIds);
	
	/**
	 * 查询用户的权限列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	List<SysMenuEntity> queryUserList(Long userId);
}
