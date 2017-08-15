/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.service.impl;

import com.richstonedt.garnet.modules.sys.dao.SysRoleMenuDao;
import com.richstonedt.garnet.modules.sys.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 角色与菜单对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:44:35
 * @since garnet-core-be-fe 1.0.0
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

	/**
	 * The Sys role menu dao.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;

	/**
	 * Save or update.
	 *
	 * @param roleId     the role id
	 * @param menuIdList the menu id list
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	@Transactional
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		//先删除角色与菜单关系
		sysRoleMenuDao.delete(roleId);

		if(menuIdList.size() == 0){
			return ;
		}

		//保存角色与菜单关系
		Map<String, Object> map = new HashMap<>();
		map.put("roleId", roleId);
		map.put("menuIdList", menuIdList);
		sysRoleMenuDao.save(map);
	}

	/**
	 * 根据角色ID，获取菜单ID列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public List<Long> queryMenuIdList(Long roleId) {
		return sysRoleMenuDao.queryMenuIdList(roleId);
	}

}
