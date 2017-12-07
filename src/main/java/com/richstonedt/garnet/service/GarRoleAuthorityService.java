/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarRoleAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <b><code>GarRoleAuthorityService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 11:08
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
public interface GarRoleAuthorityService extends BaseService<GarRoleAuthority> {
    
    List<GarRoleAuthority> getRoleAuthorityByRoleId(Long roleId);

    List<GarRoleAuthority> getRoleAuthorityByAuthorityId(Long authorityId);

    void deleteRoleAuthorityByAuthorityId(Long authorityId);

    Set<Long> getAuthorityIdsByRoleIds(Set<Long> roleIds);
}
