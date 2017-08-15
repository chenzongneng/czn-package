/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.service;

import java.util.List;



/**
 * 用户与角色对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:43:24
 *
 * @since garnet-core-be-fe 1.0.0
 */
public interface SysUserRoleService {

	/**
	 * Save or update.
	 *
	 * @param userId     the user id
	 * @param roleIdList the role id list
	 * @since garnet-core-be-fe 1.0.0
	 */
	void saveOrUpdate(Long userId, List<Long> roleIdList);
	
	/**
	 * 根据用户ID，获取角色ID列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	List<Long> queryRoleIdList(Long userId);

	/**
	 * Delete.
	 *
	 * @param userId the user id
	 * @since garnet-core-be-fe 1.0.0
	 */
	void delete(Long userId);
}
