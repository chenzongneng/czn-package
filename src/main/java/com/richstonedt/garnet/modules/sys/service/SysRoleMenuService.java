/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.service;

import java.util.List;



/**
 * 角色与菜单对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:30
 * @since garnet-core-be-fe 1.0.0
 */
public interface SysRoleMenuService {

	/**
	 * Save or update.
	 *
	 * @param roleId     the role id
	 * @param menuIdList the menu id list
	 * @since garnet-core-be-fe 1.0.0
	 */
	void saveOrUpdate(Long roleId, List<Long> menuIdList);
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	List<Long> queryMenuIdList(Long roleId);
	
}
