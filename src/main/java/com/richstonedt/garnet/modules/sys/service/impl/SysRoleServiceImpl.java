/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.service.impl;

import com.richstonedt.garnet.modules.sys.dao.SysRoleDao;
import com.richstonedt.garnet.modules.sys.entity.SysRoleEntity;
import com.richstonedt.garnet.modules.sys.service.SysRoleDeptService;
import com.richstonedt.garnet.modules.sys.service.SysRoleMenuService;
import com.richstonedt.garnet.modules.sys.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 * 角色
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:45:12
 * @since garnet-core-be-fe 1.0.0
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

	/**
	 * The Sys role dao.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Autowired
	private SysRoleDao sysRoleDao;

	/**
	 * The Sys role menu service.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	/**
	 * The Sys role dept service.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Autowired
	private SysRoleDeptService sysRoleDeptService;

	/**
	 * Query object sys role entity.
	 *
	 * @param roleId the role id
	 * @return the sys role entity
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public SysRoleEntity queryObject(Long roleId) {
		return sysRoleDao.queryObject(roleId);
	}

	/**
	 * Query list list.
	 *
	 * @param map the map
	 * @return the list
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public List<SysRoleEntity> queryList(Map<String, Object> map) {
		return sysRoleDao.queryList(map);
	}

	/**
	 * Query total int.
	 *
	 * @param map the map
	 * @return the int
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysRoleDao.queryTotal(map);
	}

	/**
	 * Save.
	 *
	 * @param role the role
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	@Transactional
	public void save(SysRoleEntity role) {
		role.setCreateTime(new Date());
		sysRoleDao.save(role);
		
		//保存角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

		//保存角色与部门关系
		sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
	}

	/**
	 * Update.
	 *
	 * @param role the role
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	@Transactional
	public void update(SysRoleEntity role) {
		sysRoleDao.update(role);
		
		//更新角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());

		//保存角色与部门关系
		sysRoleDeptService.saveOrUpdate(role.getRoleId(), role.getDeptIdList());
	}

	/**
	 * Delete batch.
	 *
	 * @param roleIds the role ids
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	@Transactional
	public void deleteBatch(Long[] roleIds) {
		sysRoleDao.deleteBatch(roleIds);
	}

}
