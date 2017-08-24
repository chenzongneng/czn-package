/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.role.dao;

import com.richstonedt.garnet.system.role.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b><code>RoleDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/8/21 15:22
 *
 * @author Sun Jinpeng
 * @version 1.0.0
 * @since garnet-core-be-fe 1.0.0
 */
@Mapper
public interface RoleDao {

    /**
     * Get roles list.
     *
     * @param offset the offset
     * @param limit  the limit
     * @param tenantId the tenant Id
     * @return the list
     * @since garnet-core-be-fe 1.0.0
     */
    List<Role> getRoleLists(@Param("offset") Integer offset, @Param("limit")Integer limit,@Param("tenantId")Integer tenantId,@Param("name") String name);

    /**
     * Gets role by id.
     *
     * @param id the role id
     * @return the role by id
     * @since garnet-core-be-fe 1.0.0
     */
    Role getRoleById(@Param("id")Integer id);

    /**
     * Insert role.
     *
     * @param role the role
     * @since garnet-core-be-fe 1.0.0
     */
    void insertRole(Role role);

    /**
     * Update role.
     *
     * @param role the role
     * @since garnet-core-be-fe 1.0.0
     */
    void updateRole(Role role);

    /**
     * Delete role.
     *
     * @param id the role id
     * @since garnet-core-be-fe 1.0.0
     */
    void deleteRole(@Param("id") Integer id);
}
