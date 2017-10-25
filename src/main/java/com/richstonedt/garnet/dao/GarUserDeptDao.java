/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.dao;

import com.richstonedt.garnet.model.GarUserDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b><code>GarUserDeptDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/17 9:45
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Mapper
public interface GarUserDeptDao extends BaseDao<GarUserDept> {

    /**
     * Query object by dept id list.
     *
     * @param deptId the dept id
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarUserDept> queryObjectByDeptId(@Param(value = "deptId") Long deptId);

    /**
     * Delte user dept by dept id.
     *
     * @param deptId the dept id
     * @since garnet-core-be-fe 0.1.0
     */
    void deleteUserDeptByDeptId(@Param(value = "deptId") Long deptId);
}
