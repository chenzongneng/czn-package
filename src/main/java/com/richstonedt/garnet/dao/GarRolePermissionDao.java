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

    void saveRolePermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    void deleteRolePermissionByRoleId(@Param("roleId") Long roleId);

    void deleteBatchByRoleIds(@Param("roleIds") List<Long> roleIds);

    List<String> getPermissionNamesByRoleId(@Param("roleId") Long roleId);

    List<Long> getPermissionIdsByRoleId(@Param("roleId") Long roleId);
}
