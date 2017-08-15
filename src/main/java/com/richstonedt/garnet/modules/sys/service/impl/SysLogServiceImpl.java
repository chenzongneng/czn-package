/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.service.impl;

import com.richstonedt.garnet.modules.sys.dao.SysLogDao;
import com.richstonedt.garnet.modules.sys.entity.SysLogEntity;
import com.richstonedt.garnet.modules.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * The type Sys log service.
 *
 * @since garnet-core-be-fe 1.0.0
 */
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {

	/**
	 * The Sys log dao.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Autowired
	private SysLogDao sysLogDao;

	/**
	 * Query object sys log entity.
	 *
	 * @param id the id
	 * @return the sys log entity
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public SysLogEntity queryObject(Long id){
		return sysLogDao.queryObject(id);
	}

	/**
	 * Query list list.
	 *
	 * @param map the map
	 * @return the list
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public List<SysLogEntity> queryList(Map<String, Object> map){
		return sysLogDao.queryList(map);
	}

	/**
	 * Query total int.
	 *
	 * @param map the map
	 * @return the int
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public int queryTotal(Map<String, Object> map){
		return sysLogDao.queryTotal(map);
	}

	/**
	 * Save.
	 *
	 * @param sysLog the sys log
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public void save(SysLogEntity sysLog){
		sysLogDao.save(sysLog);
	}

	/**
	 * Delete.
	 *
	 * @param id the id
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public void delete(Long id){
		sysLogDao.delete(id);
	}

	/**
	 * Delete batch.
	 *
	 * @param ids the ids
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public void deleteBatch(Long[] ids){
		sysLogDao.deleteBatch(ids);
	}
	
}
