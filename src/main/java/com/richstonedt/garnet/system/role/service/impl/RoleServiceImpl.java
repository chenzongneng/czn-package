/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.role.service.impl;

import com.richstonedt.garnet.system.role.dao.RoleDao;
import com.richstonedt.garnet.system.role.entity.SysRole;
import com.richstonedt.garnet.system.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b><code>RoleServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/8/21 15:21
 *
 * @author Sun Jinpeng
 * @version 1.0.0
 * @since garnet-core-be-fe 1.0.0
 */
@Service
public class RoleServiceImpl  implements RoleService{

    /**
     * The Role dao.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private RoleDao roleDao;

    /**
     * Gets role lists.
     *
     * @param page   the page
     * @param limit  the limit
     * @param roleId the role id
     * @return the role lists
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public List<SysRole> getRoleLists(int page, int limit, int roleId) {
        int offset = (page - 1) * limit;
        return roleDao.getRoleLists(offset,limit,roleId);
    }

    /**
     * Search role list.
     *
     * @param roleId   the role id
     * @param roleName the role name
     * @return the list
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public List<SysRole> searchRole(int roleId, String roleName) {
        return roleDao.searchRoles(roleId,roleName);
    }
}
