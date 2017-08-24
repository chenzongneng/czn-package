/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.role.service;

import com.richstonedt.garnet.system.role.entity.Role;

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
    List<Role> getRoleLists(int page, int limit, Integer roleId, String roleName);

    /**
     * Save role.
     *
     * @param role     the roles
     * @since garnet-core-be-fe 1.0.0
     */
    void saveRole(Role role);

    /**
     * Update role.
     *
     * @param role     the role
     * @since garnet-core-be-fe 1.0.0
     */
    void updateRole(Role role);

    /**
     * Delete role.
     *
     * @param idLists the role ids
     * @since garnet-core-be-fe 1.0.0
     */
    void deleteRole(List<Integer> idLists);

    /**
     * Gets role by id.
     *
     * @param roleId the role id
     * @return the role by id
     * @since garnet-core-be-fe 1.0.0
     */
    Role getRoleById(Integer roleId);
}
