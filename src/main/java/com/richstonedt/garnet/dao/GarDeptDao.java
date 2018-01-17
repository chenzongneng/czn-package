/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.dao;

import com.richstonedt.garnet.model.GarDept;
import com.richstonedt.garnet.model.view.model.GarVMDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <b><code>GarDeptDao</code></b>
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
public interface GarDeptDao extends BaseDao<GarDept> {

    /**
     * Query detp id list list.
     *
     * @param parentDeptId the parent dept id
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<Long> queryDetpIdList(@Param(value = "parentDeptId") Long parentDeptId);

    /**
     * Query objects list.
     *
     * @param tenantId   the tenant Id
     * @param subList    the sub list
     * @return the list
     * @since garnet-core-be-fe 1.0.0
     */
    List<GarDept> queryDeptList(@Param(value = "tenantId") Long tenantId, @Param(value = "subList") String subList);

    /**
     * Gets dept by parent dept id.
     *
     * @param parentDeptId the parent dept id
     * @return the dept by parent dept id
     * @since garnet -core-be-fe 0.1.0
     */
    GarDept getDeptByParentDeptId(@Param(value = "parentDeptId") Long parentDeptId);

    int getTotalDeptByParam(Map<String, Object> params);
}
