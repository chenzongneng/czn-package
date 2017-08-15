/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.service.impl;

import com.qiniu.util.StringUtils;
import com.richstonedt.garnet.modules.sys.dao.SysDeptDao;
import com.richstonedt.garnet.modules.sys.entity.SysDeptEntity;
import com.richstonedt.garnet.modules.sys.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * The type Sys dept service.
 *
 * @since garnet-core-be-fe 1.0.0
 */
@Service("sysDeptService")
public class SysDeptServiceImpl implements SysDeptService {

	/**
	 * The Sys dept dao.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Autowired
	private SysDeptDao sysDeptDao;

	/**
	 * Query object sys dept entity.
	 *
	 * @param deptId the dept id
	 * @return the sys dept entity
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public SysDeptEntity queryObject(Long deptId){
		return sysDeptDao.queryObject(deptId);
	}

	/**
	 * Query list list.
	 *
	 * @param map the map
	 * @return the list
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public List<SysDeptEntity> queryList(Map<String, Object> map){
		return sysDeptDao.queryList(map);
	}

	/**
	 * Save.
	 *
	 * @param sysDept the sys dept
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public void save(SysDeptEntity sysDept){
		sysDeptDao.save(sysDept);
	}

	/**
	 * Update.
	 *
	 * @param sysDept the sys dept
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public void update(SysDeptEntity sysDept){
		sysDeptDao.update(sysDept);
	}

	/**
	 * Delete.
	 *
	 * @param deptId the dept id
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public void delete(Long deptId){
		sysDeptDao.delete(deptId);
	}

	/**
	 * 查询子部门ID列表
	 *
	 * @param parentId  上级部门ID
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public List<Long> queryDetpIdList(Long parentId) {
		return sysDeptDao.queryDetpIdList(parentId);
	}

	/**
	 * 获取子部门ID(包含本部门ID)，用于数据过滤
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public String getSubDeptIdList(Long deptId){
		//部门及子部门ID列表
		List<Long> deptIdList = new ArrayList<>();

		//获取子部门ID
		List<Long> subIdList = queryDetpIdList(deptId);
		getDeptTreeList(subIdList, deptIdList);

		//添加本部门
		deptIdList.add(deptId);
		return StringUtils.join(deptIdList, ",");
	}

	/**
	 * 递归
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList){
		for(Long deptId : subIdList){
			List<Long> list = queryDetpIdList(deptId);
			if(list.size() > 0){
				getDeptTreeList(list, deptIdList);
			}

			deptIdList.add(deptId);
		}
	}
}
