/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b><code>GarRolePermissionDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 10:56
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Mapper
public interface GarRolePermissionDao {

    /**
     * Save role permission.
     *
     * @param roleId       the role id
     * @param permissionId the permission id
     * @since garnet-core-be-fe 0.1.0
     */
    void saveRolePermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    /**
     * Delete role permission by role id.
     *
     * @param roleId the role id
     * @since garnet-core-be-fe 0.1.0
     */
    void deleteRolePermissionByRoleId(@Param("roleId") Long roleId);

    /**
     * Delete batch by role ids.
     *
     * @param roleIds the role ids
     * @since garnet-core-be-fe 0.1.0
     */
    void deleteBatchByRoleIds(@Param("roleIds") List<Long> roleIds);

    /**
     * Gets permission names by role id.
     *
     * @param roleId the role id
     * @return the permission names by role id
     * @since garnet-core-be-fe 0.1.0
     */
    List<String> getPermissionNamesByRoleId(@Param("roleId") Long roleId);

    /**
     * Gets permission ids by role id.
     *
     * @param roleId the role id
     * @return the permission ids by role id
     * @since garnet-core-be-fe 0.1.0
     */
    List<Long> getPermissionIdsByRoleId(@Param("roleId") Long roleId);

    /**
     * Delete by permission id.
     *
     * @param permissionIds the permission ids
     * @since garnet-core-be-fe 0.1.0
     */
    void deleteByPermissionId(@Param("permissionId") Long permissionIds);

    /**
     * Delete batch by permission ids.
     *
     * @param permissionIds the permission ids
     * @since garnet-core-be-fe 0.1.0
     */
    void deleteBatchByPermissionIds(@Param("permissionIds") List<Long> permissionIds);
}
