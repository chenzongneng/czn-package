/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarRoleDao;
import com.richstonedt.garnet.model.GarRole;
import com.richstonedt.garnet.model.GarRoleDept;
import com.richstonedt.garnet.model.GarUser;
import com.richstonedt.garnet.model.view.model.GarVMRole;
import com.richstonedt.garnet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>GarRoleServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/25 14:07
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class GarRoleServiceImpl implements GarRoleService {

    /**
     * The Role dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarRoleDao roleDao;

    /**
     * The Token service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarTokenService tokenService;

    /**
     * The Tenant service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarTenantService tenantService;

    /**
     * The Application service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarApplicationService applicationService;

    /**
     * The Dept service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarDeptService deptService;

    /**
     * The Role dept service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarRoleDeptService roleDeptService;

    /**
     * Save.
     *
     * @param garRole the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarRole garRole) {
        roleDao.save(garRole);
    }

    /**
     * Update.
     *
     * @param garRole the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarRole garRole) {
        update(garRole);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void deleteById(Long id) {
        roleDao.deleteById(id);
    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Long> ids) {
        roleDao.deleteBatch(ids);
    }

    /**
     * Query object t.
     *
     * @param id the id
     * @return the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarRole queryObject(Long id) {
        return roleDao.queryObject(id);
    }

    /**
     * Query objects list.
     *
     * @param searchName the search name
     * @param page       the offset
     * @param limit      the limit
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarRole> queryObjects(String searchName, Integer page, Integer limit) {
        Integer offset = (page - 1) * limit;
        return roleDao.queryObjects(searchName, limit, offset);
    }

    /**
     * Query total int.
     *
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal() {
        return roleDao.queryTotal();
    }

    /**
     * Query role list list.
     *
     * @param token      the token
     * @param searchName the search name
     * @param page       the page
     * @param limit      the limit
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarVMRole> queryRoleList(String token, String searchName, Integer page, Integer limit) {
        Integer offset = (page - 1) * limit;
        GarUser currentUser = tokenService.getUserInfoByToken(token);
        List<GarRole> garRoles = roleDao.queryRoleList(currentUser.getTenantId(), searchName, limit, offset);
        if (CollectionUtils.isEmpty(garRoles)) {
            return null;
        }
        List<GarVMRole> result = new ArrayList<>();
        for (GarRole role : garRoles) {
            result.add(convertRoleToVmRole(role));
        }
        return result;
    }

    /**
     * Convert role to vm role gar vm role.
     *
     * @param role the role
     * @return the gar vm role
     * @since garnet-core-be-fe 0.1.0
     */
    private GarVMRole convertRoleToVmRole(GarRole role) {
        GarVMRole vmRole = new GarVMRole();
        String tenantName = tenantService.queryObject(role.getTenantId()).getName();
        String appName = applicationService.queryObject(role.getAppId()).getName();

        // 获取该角色的部门列表
        List<String> deptNameList = new ArrayList<>();
        List<Long> deptIdList = new ArrayList<>();
        List<GarRoleDept> roleDeptList = roleDeptService.getRoleDeptByRoleId(role.getRoleId());
        if (!CollectionUtils.isEmpty(roleDeptList)) {
            for (GarRoleDept roleDept : roleDeptList) {
                deptIdList.add(roleDept.getDeptId());
                deptNameList.add(deptService.queryObject(roleDept.getDeptId()).getName());
            }
        }
        vmRole.setDeptIdList(deptIdList);
        vmRole.setDeptNameList(deptNameList);

        // todo  获取该角色的权限列表

        vmRole.setTenantId(role.getTenantId());
        vmRole.setAppId(role.getAppId());
        vmRole.setTenantName(tenantName);
        vmRole.setAppName(appName);
        vmRole.setRoleId(role.getRoleId());
        vmRole.setName(role.getName());
        vmRole.setRemark(role.getRemark());
        vmRole.setCreateTime(role.getCreateTime());
        return vmRole;
    }
}
