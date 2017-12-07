/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.dao;

import com.richstonedt.garnet.model.GarRoleAuthority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <b><code>GarRoleAuthorityDao</code></b>
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
public interface GarRoleAuthorityDao extends BaseDao<GarRoleAuthority> {

    List<GarRoleAuthority> getRoleAuthorityByRoleId(@Param(value = "roleId") Long roleId);

    List<GarRoleAuthority> getRoleAuthorityByAuthorityId(@Param(value = "authorityId") Long authorityId);

    void deleteRoleAuthorityByAuthorityId(@Param(value = "authorityId") Long authorityId);

    Set<Long> getAuthorityIdsByRoleIds(@Param(value = "roleIds")Set<Long> roleIds);
}
