/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarRoleDept;

import java.util.List;

/**
 * <b><code>GarRoleDeptService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/17 10:28
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
public interface GarRoleDeptService extends BaseService<GarRoleDept> {

    /**
     * Gets role dept by role id.
     *
     * @param roleId the role id
     * @return the role dept by role id
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarRoleDept> getRoleDeptByRoleId(Long roleId);

    /**
     * Gets role dept by dept id.
     *
     * @param deptId the dept id
     * @return the role dept by dept id
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarRoleDept> getRoleDeptByDeptId(Long deptId);

    /**
     * Delete role dept by dept id.
     *
     * @param deptId the dept id
     * @since garnet-core-be-fe 0.1.0
     */
    void deleteRoleDeptByDeptId(Long deptId);
}
