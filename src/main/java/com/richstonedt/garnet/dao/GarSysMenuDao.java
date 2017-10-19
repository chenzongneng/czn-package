/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.dao;

import com.richstonedt.garnet.model.GarSysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b><code>GarSysMenuDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/19 16:56
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Mapper
public interface GarSysMenuDao {

    /**
     * Query object gar sys menu.
     *
     * @param id the id
     * @return the gar sys menu
     * @since garnet-core-be-fe 0.1.0
     */
    GarSysMenu queryObject(@Param(value = "id") Long id);

    /**
     * Query list parent id list.
     *
     * @param parentId the parent id
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarSysMenu> queryListParentId(@Param(value = "parentId") Long parentId);

    /**
     * Query list list.
     *
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarSysMenu> queryList();
}
