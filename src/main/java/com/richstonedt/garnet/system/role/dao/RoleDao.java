/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.role.dao;

import com.richstonedt.garnet.system.role.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

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
     * @param roleId the role id
     * @return the list
     * @since garnet-core-be-fe 1.0.0
     */
    List<SysRole> getRoleLists(Integer offset, Integer limit, Integer roleId);

    /**
     * Search roles list.
     *
     * @param roleId   the role id
     * @param roleName the role name
     * @return the list
     * @since garnet-core-be-fe 1.0.0
     */
    List<SysRole> searchRoles(Integer roleId,String roleName);

    /**
     * Gets role by id.
     *
     * @param id the id
     * @return the role by id
     * @since garnet-core-be-fe 1.0.0
     */
    SysRole getRoleById(Integer id);

    /**
     * Insert role.
     *
     * @param role the role
     * @since garnet-core-be-fe 1.0.0
     */
    void insertRole(SysRole role);

    /**
     * Update role.
     *
     * @param role the role
     * @since garnet-core-be-fe 1.0.0
     */
    void updateRole(SysRole role);

    /**
     * Delete role.
     *
     * @param roleId the role id
     * @since garnet-core-be-fe 1.0.0
     */
    void deleteRole(Integer roleId);
}
