/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.service.impl;

import com.richstonedt.garnet.modules.sys.dao.SysUserDao;
import com.richstonedt.garnet.modules.sys.entity.SysUserEntity;
import com.richstonedt.garnet.modules.sys.service.SysUserRoleService;
import com.richstonedt.garnet.modules.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 * @since garnet-core-be-fe 1.0.0
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

	/**
	 * The Sys user dao.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Autowired
	private SysUserDao sysUserDao;

	/**
	 * The Sys user role service.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * 查询用户的所有权限
	 *
	 * @param userId  用户ID
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public List<String> queryAllPerms(Long userId) {
		return sysUserDao.queryAllPerms(userId);
	}

	/**
	 * 查询用户的所有菜单ID
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return sysUserDao.queryAllMenuId(userId);
	}

	/**
	 * 根据用户名，查询系统用户
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public SysUserEntity queryByUserName(String username) {
		return sysUserDao.queryByUserName(username);
	}

	/**
	 * 根据用户ID，查询用户
	 *
	 * @param userId the userid
	 * @return  SysUserEntity
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public SysUserEntity queryObject(Long userId) {
		return sysUserDao.queryObject(userId);
	}

	/**
	 * 查询用户列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public List<SysUserEntity> queryList(Map<String, Object> map){
		return sysUserDao.queryList(map);
	}

	/**
	 * 查询总数
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysUserDao.queryTotal(map);
	}

	/**
	 * 保存用户
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	@Transactional
	public void save(SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		sysUserDao.save(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	/**
	 * 修改用户
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	@Transactional
	public void update(SysUserEntity user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
		}
		sysUserDao.update(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	/**
	 * 删除用户
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	@Transactional
	public void deleteBatch(Long[] userId) {
		sysUserDao.deleteBatch(userId);
	}

	/**
	 * 修改密码
	 *
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public int updatePassword(Long userId, String password, String newPassword) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("newPassword", newPassword);
		return sysUserDao.updatePassword(map);
	}

}
