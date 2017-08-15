/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.service;

import com.richstonedt.garnet.modules.sys.entity.SysLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统日志
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-08 10:40:56
 * @since garnet-core-be-fe 1.0.0
 */
public interface SysLogService {

	/**
	 * Query object sys log entity.
	 *
	 * @param id the id
	 * @return the sys log entity
	 * @since garnet-core-be-fe 1.0.0
	 */
	SysLogEntity queryObject(Long id);

	/**
	 * Query list list.
	 *
	 * @param map the map
	 * @return the list
	 * @since garnet-core-be-fe 1.0.0
	 */
	List<SysLogEntity> queryList(Map<String, Object> map);

	/**
	 * Query total int.
	 *
	 * @param map the map
	 * @return the int
	 * @since garnet-core-be-fe 1.0.0
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * Save.
	 *
	 * @param sysLog the sys log
	 * @since garnet-core-be-fe 1.0.0
	 */
	void save(SysLogEntity sysLog);

	/**
	 * Delete.
	 *
	 * @param id the id
	 * @since garnet-core-be-fe 1.0.0
	 */
	void delete(Long id);

	/**
	 * Delete batch.
	 *
	 * @param ids the ids
	 * @since garnet-core-be-fe 1.0.0
	 */
	void deleteBatch(Long[] ids);
}
