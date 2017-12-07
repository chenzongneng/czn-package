/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarAuthorityPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <b><code>GarAuthorityPermissionService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 15:11
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
public interface GarAuthorityPermissionService extends BaseService<GarAuthorityPermission> {

    List<GarAuthorityPermission> getAuthorityPermissionByAuthorityId(Long authorityId);

    List<GarAuthorityPermission> getAuthorityPermissionByPermissionId(Long permissionId);

    void deleteAuthorityPermissionByAuthorityId(Long permissionId);

    Set<Long> getPermissionIdsByAuthorityIds(Set<Long> ids);
}
