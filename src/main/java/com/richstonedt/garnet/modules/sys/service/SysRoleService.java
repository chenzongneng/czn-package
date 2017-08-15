/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.service;

import com.richstonedt.garnet.modules.sys.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;


/**
 * 角色
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:52
 * @since garnet-core-be-fe 1.0.0
 */
public interface SysRoleService {

	/**
	 * Query object sys role entity.
	 *
	 * @param roleId the role id
	 * @return the sys role entity
	 * @since garnet-core-be-fe 1.0.0
	 */
	SysRoleEntity queryObject(Long roleId);

	/**
	 * Query list list.
	 *
	 * @param map the map
	 * @return the list
	 * @since garnet-core-be-fe 1.0.0
	 */
	List<SysRoleEntity> queryList(Map<String, Object> map);

	/**
	 * Query total int.
	 *
	 * @param map the map
	 * @return the int
	 * @since garnet-core-be-fe 1.0.0
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * Save.
	 *
	 * @param role the role
	 * @since garnet-core-be-fe 1.0.0
	 */
	void save(SysRoleEntity role);

	/**
	 * Update.
	 *
	 * @param role the role
	 * @since garnet-core-be-fe 1.0.0
	 */
	void update(SysRoleEntity role);

	/**
	 * Delete batch.
	 *
	 * @param roleIds the role ids
	 * @since garnet-core-be-fe 1.0.0
	 */
	void deleteBatch(Long[] roleIds);

}
