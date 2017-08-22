/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.role.service;

import com.richstonedt.garnet.system.role.entity.SysRole;

import java.util.List;

/**
 * <b><code>RoleService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/8/21 15:20
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 1.0.0
 */
public interface RoleService {

    /**
     * Gets role lists.
     *
     * @param page   the page
     * @param limit  the limit
     * @param roleId the role id
     * @return the role lists
     * @since garnet-core-be-fe 1.0.0
     */
    List<SysRole> getRoleLists(int page,int limit,int roleId);

    /**
     * Search role list.
     *
     * @param roleId   the role id
     * @param roleName the role name
     * @return the list
     * @since garnet-core-be-fe 1.0.0
     */
    List<SysRole> searchRole(int roleId,String roleName);

    /**
     * Save role.
     *
     * @param role     the roles
     * @param roleId   the roleId  该角色的id
     * @param roleType the role type
     * @param tenant   the tenant
     * @since garnet-core-be-fe 1.0.0
     */
    void saveRole(SysRole role, Integer roleId,Integer roleType,Integer tenant);

    /**
     * Update role.
     *
     * @param role     the role
     * @param roleType the role type
     * @param tenant   the tenant
     *                 @since garnet-core-be-fe 1.0.0
     */
    void updateRole(SysRole role,Integer roleType,Integer tenant);
}
