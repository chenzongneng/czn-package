/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarDept;
import com.richstonedt.garnet.model.view.model.GarVMDept;

import java.util.List;
import java.util.Map;

/**
 * <b><code>GarDeptService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/23 10:06
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet -core-be-fe 0.1.0
 */
public interface GarDeptService extends BaseService<GarDept> {

    /**
     * Query detp id list list.
     *
     * @param parentDeptId the parent dept id
     * @return the list
     * @since garnet -core-be-fe 0.1.0
     */
    List<Long> queryDetpIdList(Long parentDeptId);

    /**
     * Gets user dept list.
     *
     * @param userId the user id
     * @return the user dept list
     * @since garnet -core-be-fe 0.1.0
     */
    List<GarVMDept> getUserDeptList(Long userId);

    /**
     * Gets vm dept by dept id.
     *
     * @param deptId the dept id
     * @return the vm dept by dept id
     * @since garnet -core-be-fe 0.1.0
     */
    GarVMDept getVMDeptByDeptId(Long deptId);

    /**
     * Save vm dept.
     *
     * @param vmDept the vm dept
     * @since garnet -core-be-fe 0.1.0
     */
    void saveVMDept(GarVMDept vmDept);

    /**
     * Update vm dept.
     *
     * @param vmDept the vm dept
     * @since garnet -core-be-fe 0.1.0
     */
    void updateVMDept(GarVMDept vmDept);

    /**
     * Delete vm dept.
     *
     * @param deptId the dept id
     * @since garnet -core-be-fe 0.1.0
     */
    void deleteVMDept(Long deptId);

    List<GarVMDept> queryDeptListByParams(Map<String, Object> params);

    int queryTotalMenuByParam(Map<String, Object> params);
}


