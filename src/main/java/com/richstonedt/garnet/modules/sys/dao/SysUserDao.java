/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.dao;

import com.richstonedt.garnet.modules.sys.entity.SysUserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:11
 * @since garnet-core-be-fe 1.0.0
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUserEntity> {
	
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
	 * 修改密码
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	int updatePassword(Map<String, Object> map);
}
