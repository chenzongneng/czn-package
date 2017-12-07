/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.dao;

import com.richstonedt.garnet.model.GarAuthorityPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <b><code>GarAuthorityPermissionDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 14:55
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Mapper
public interface GarAuthorityPermissionDao extends BaseDao<GarAuthorityPermission> {
    
    List<GarAuthorityPermission> getAuthorityPermissionByAuthorityId(@Param(value = "authorityId") Long authorityId);

    List<GarAuthorityPermission> getAuthorityPermissionByPermissionId(@Param(value = "permissionId") Long permissionId);

    void deleteAuthorityPermissionByAuthorityId(@Param(value = "permissionId") Long permissionId);

    Set<Long> getPermissionIdsByAuthorityIds(@Param(value = "authorityIds") Set<Long> ids);
}
