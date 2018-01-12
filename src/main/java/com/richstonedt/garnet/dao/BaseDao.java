/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <b><code>BaseDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/16 17:29
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
public interface BaseDao<T> {

	/**
	 * Save.
	 *
	 * @param t the t
     * @since garnet-core-be-fe 0.1.0
     */
	void save(T t);

	/**
	 * Update int.
	 *
     * @param t the T
     * @since garnet-core-be-fe 1.0.0
	 */
	void update(T t);

    /**
     * Delete by id.
     *
     * @param id the id
     * @since garnet-core-be-fe 1.0.0
     */
    void deleteById(@Param(value = "id") Long id);

	/**
	 * Delete batch int.
	 *
     * @param ids the id
     * @since garnet-core-be-fe 1.0.0
	 */
    void deleteBatch(@Param(value = "ids") List<Long> ids);

	/**
	 * Query object t.
	 *
	 * @param id the id
	 * @return the t
	 * @since garnet-core-be-fe 1.0.0
	 */
    T queryObject(@Param(value = "id") Long id);

	/**
	 * Query total int.
	 *
	 * @param params     the params
	 * @return the int
	 * @since garnet-core-be-fe 1.0.0
	 */
	int queryTotal(Map<String,Object> params);

    /**
     * Query objects list.
     *
     * @param params     the params
     * @return the list
     * @since garnet-core-be-fe 1.0.0
     */
    List<T> queryObjects(Map<String,Object> params);
}
