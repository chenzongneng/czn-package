/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.service.impl;

import com.richstonedt.garnet.modules.sys.dao.SysUserRoleDao;
import com.richstonedt.garnet.modules.sys.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * 用户与角色对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:45:48
 * @since garnet-core-be-fe 1.0.0
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {

	/**
	 * The Sys user role dao.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Autowired
	private SysUserRoleDao sysUserRoleDao;

	/**
	 * Save or update.
	 *
	 * @param userId     the user id
	 * @param roleIdList the role id list
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		if(roleIdList.size() == 0){
			return ;
		}
		
		//先删除用户与角色关系
		sysUserRoleDao.delete(userId);
		
		//保存用户与角色关系
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("roleIdList", roleIdList);
		sysUserRoleDao.save(map);
	}

	/**
	 * 根据用户ID，获取角色ID列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return sysUserRoleDao.queryRoleIdList(userId);
	}

	/**
	 * Delete.
	 *
	 * @param userId the user id
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public void delete(Long userId) {
		sysUserRoleDao.delete(userId);
	}
}
