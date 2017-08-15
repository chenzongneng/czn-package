/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.dao;

import java.util.List;
import java.util.Map;

/**
 * 基础Dao(还需在XML文件里，有对应的SQL语句)
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:31:36
 * @since garnet-core-be-fe 1.0.0
 */
public interface BaseDao<T> {

	/**
	 * Save.
	 *
	 * @param t the t
	 * @since garnet-core-be-fe 1.0.0
	 */
	void save(T t);

	/**
	 * Save.
	 *
	 * @param map the map
	 * @since garnet-core-be-fe 1.0.0
	 */
	void save(Map<String, Object> map);

	/**
	 * Save batch.
	 *
	 * @param list the list
	 * @since garnet-core-be-fe 1.0.0
	 */
	void saveBatch(List<T> list);

	/**
	 * Update int.
	 *
	 * @param t the t
	 * @return the int
	 * @since garnet-core-be-fe 1.0.0
	 */
	int update(T t);

	/**
	 * Update int.
	 *
	 * @param map the map
	 * @return the int
	 * @since garnet-core-be-fe 1.0.0
	 */
	int update(Map<String, Object> map);

	/**
	 * Delete int.
	 *
	 * @param id the id
	 * @return the int
	 * @since garnet-core-be-fe 1.0.0
	 */
	int delete(Object id);

	/**
	 * Delete int.
	 *
	 * @param map the map
	 * @return the int
	 * @since garnet-core-be-fe 1.0.0
	 */
	int delete(Map<String, Object> map);

	/**
	 * Delete batch int.
	 *
	 * @param id the id
	 * @return the int
	 * @since garnet-core-be-fe 1.0.0
	 */
	int deleteBatch(Object[] id);

	/**
	 * Query object t.
	 *
	 * @param id the id
	 * @return the t
	 * @since garnet-core-be-fe 1.0.0
	 */
	T queryObject(Object id);

	/**
	 * Query list list.
	 *
	 * @param map the map
	 * @return the list
	 * @since garnet-core-be-fe 1.0.0
	 */
	List<T> queryList(Map<String, Object> map);

	/**
	 * Query total int.
	 *
	 * @param map the map
	 * @return the int
	 * @since garnet-core-be-fe 1.0.0
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * Query total int.
	 *
	 * @return the int
	 * @since garnet-core-be-fe 1.0.0
	 */
	int queryTotal();
}
