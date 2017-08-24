/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.role.service.impl;

import com.richstonedt.garnet.system.department.service.DepartmentService;
import com.richstonedt.garnet.system.role.dao.RoleDao;
import com.richstonedt.garnet.system.role.entity.Role;
import com.richstonedt.garnet.system.role.service.RoleService;
import com.richstonedt.garnet.system.tenant.entity.Tenant;
import com.richstonedt.garnet.system.tenant.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
public class RoleServiceImpl implements RoleService {

    /**
     * The Role dao.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private RoleDao roleDao;

    /**
     * the dept Service
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private DepartmentService deptService;

    /**
     * the tenant Service
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private TenantService tenantService;

    /**
     * Gets role lists.
     *
     * @param page     the page
     * @param limit    the limit
     * @param tenantId the tenant id
     * @return the role lists
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public List<Role> getRoleLists(int page, int limit, Integer tenantId, String name) {
        int offset = (page - 1) * limit;
        return convertWithDeptName(roleDao.getRoleLists(offset, limit, tenantId, name));
    }

    /**
     * Save role.
     *
     * @param role     the role
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void saveRole(Role role) {
        roleDao.insertRole(role);
    }

    /**
     * Update role.
     *
     * @param role   the role
     * @param deptId the dept id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void updateRole(Role role, Integer deptId) {
        if (deptId != null) {
            Long temTenantId = deptService.getDepartmentById(deptId.longValue()).getTenantId();
            role.setTenantId(temTenantId.intValue());
        }
        roleDao.updateRole(role);
    }

    /**
     * Delete role.
     *
     * @param id the id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void deleteRole(Integer id) {
        roleDao.deleteRole(id);
    }

    /**
     * Gets role by id.
     *
     * @param id the id
     * @return the role by id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public Role getRoleById(Integer id) {
        return roleDao.getRoleById(id);
    }

    /**
     * Convert with dept name list.
     *
     * @param lists the lists
     * @return the list
     * @since garnet-core-be-fe 1.0.0
     */
    private List<Role> convertWithDeptName(List<Role> lists) {
        if (!CollectionUtils.isEmpty(lists)) {
            for (Role role : lists) {
                if (role.getTenantId() != null) {
                    Tenant tenant = tenantService.getTenantById(role.getTenantId().longValue());
                    if (tenant != null) {
                        role.setDeptName(tenant.getName());
                    }
                } else {
                    role.setDeptName("超级管理员");
                }
            }
        }
        return lists;
    }
}
