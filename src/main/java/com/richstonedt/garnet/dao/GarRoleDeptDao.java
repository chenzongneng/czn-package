/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.dao;

import com.richstonedt.garnet.model.GarRoleDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

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
public interface GarRoleDeptDao extends BaseDao<GarRoleDept> {

    /**
     * Gets role dept by role id.
     *
     * @param roleId the role id
     * @return the role dept by role id
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarRoleDept> getRoleDeptByRoleId(@Param(value = "roleId") Long roleId);

    /**
     * Gets role dept by dept id.
     *
     * @param deptId the dept id
     * @return the role dept by dept id
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarRoleDept> getRoleDeptByDeptId(@Param(value = "departmentId") Long deptId);

    /**
     * Delete role dept by dept id.
     *
     * @param deptId the dept id
     * @since garnet-core-be-fe 0.1.0
     */
    void deleteRoleDeptByDeptId(@Param(value = "departmentId") Long deptId);

    /**
     * Gets role ids by dept ids.
     *
     * @param deptIds the dept ids
     * @return the role ids by dept ids
     * @since garnet-core-be-fe 0.1.0
     */
    Set<Long> getRoleIdsByDeptIds(@Param(value = "departmentIds")Set<Long> deptIds);
}
