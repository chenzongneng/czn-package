/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.service.impl;

import com.richstonedt.garnet.common.utils.Constant;
import com.richstonedt.garnet.common.utils.Constant.MenuType;
import com.richstonedt.garnet.modules.sys.dao.SysMenuDao;
import com.richstonedt.garnet.modules.sys.entity.SysMenuEntity;
import com.richstonedt.garnet.modules.sys.service.SysMenuService;
import com.richstonedt.garnet.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * The type Sys menu service.
 *
 * @since garnet-core-be-fe 1.0.0
 */
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

	/**
	 * The Sys menu dao.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Autowired
	private SysMenuDao sysMenuDao;

	/**
	 * The Sys user service.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Autowired
	private SysUserService sysUserService;

	/**
	 * 根据父菜单，查询子菜单
	 *
	 * @param parentId 父菜单ID
	 * @param menuIdList  用户菜单ID
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenuEntity> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<SysMenuEntity> userMenuList = new ArrayList<>();
		for(SysMenuEntity menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	/**
	 * 根据父菜单，查询子菜单
	 *
	 * @param parentId 父菜单ID
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId) {
		return sysMenuDao.queryListParentId(parentId);
	}

	/**
	 * 获取不包含按钮的菜单列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public List<SysMenuEntity> queryNotButtonList() {
		return sysMenuDao.queryNotButtonList();
	}

	/**
	 * 获取用户菜单列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public List<SysMenuEntity> getUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == Constant.SUPER_ADMIN){
			return getAllMenuList(null);
		}
		
		//用户菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}

	/**
	 * 查询菜单
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public SysMenuEntity queryObject(Long menuId) {
		return sysMenuDao.queryObject(menuId);
	}

	/**
	 * 查询菜单列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public List<SysMenuEntity> queryList(Map<String, Object> map) {
		return sysMenuDao.queryList(map);
	}

	/**
	 * 查询总数
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysMenuDao.queryTotal(map);
	}

	/**
	 * 保存菜单
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public void save(SysMenuEntity menu) {
		sysMenuDao.save(menu);
	}

	/**
	 * 修改
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public void update(SysMenuEntity menu) {
		sysMenuDao.update(menu);
	}

	/**
	 * 删除
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	@Transactional
	public void deleteBatch(Long[] menuIds) {
		sysMenuDao.deleteBatch(menuIds);
	}

	/**
	 * 查询用户的权限列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public List<SysMenuEntity> queryUserList(Long userId) {
		return sysMenuDao.queryUserList(userId);
	}

	/**
	 * 获取所有菜单列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList){
		List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();
		
		for(SysMenuEntity entity : menuList){
			if(entity.getType() == MenuType.CATALOG.getValue()){//目录
				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}
}
