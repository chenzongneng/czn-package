/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.service;

import com.richstonedt.garnet.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:43:39
 * @since garnet-core-be-fe 1.0.0
 */
public interface SysUserService {

	/**
	 * 查询用户的所有权限
	 *
	 * @param userId  用户ID
	 * @since garnet-core-be-fe 1.0.0
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	List<Long> queryAllMenuId(Long userId);

	/**
	 * 根据用户名，查询系统用户
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	SysUserEntity queryByUserName(String username);
	
	/**
	 * 根据用户ID，查询用户
	 *
	 * @param userId the userid
	 * @return  SysUserEntity
	 * @since garnet-core-be-fe 1.0.0
	 */
	SysUserEntity queryObject(Long userId);
	
	/**
	 * 查询用户列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	List<SysUserEntity> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存用户
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	void save(SysUserEntity user);
	
	/**
	 * 修改用户
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	void update(SysUserEntity user);
	
	/**
	 * 删除用户
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	void deleteBatch(Long[] userIds);
	
	/**
	 * 修改密码
	 *
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 * @since garnet-core-be-fe 1.0.0
	 */
	int updatePassword(Long userId, String password, String newPassword);
}
