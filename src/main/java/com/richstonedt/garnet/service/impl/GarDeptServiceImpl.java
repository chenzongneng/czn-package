/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarDeptDao;
import com.richstonedt.garnet.model.*;
import com.richstonedt.garnet.model.view.model.GarVMDept;
import com.richstonedt.garnet.service.*;
import com.richstonedt.garnet.utils.GarnetRsUtil;
import com.richstonedt.garnet.utils.IdGeneratorUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>GarDeptServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/23 10:07
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class GarDeptServiceImpl implements GarDeptService {

    /**
     * The Dept dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarDeptDao deptDao;

    /**
     * The User dept service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarUserDeptService userDeptService;

    /**
     * The Application service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarApplicationService applicationService;

    /**
     * The Tenant service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarTenantService tenantService;

    /**
     * The User service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarUserService userService;

    /**
     * The Role service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarRoleService roleService;

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
     * @param garDept the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarDept garDept) {
        if (garDept.getDeptId() == null) {
            garDept.setDeptId(IdGeneratorUtil.generateId());
        }
        deptDao.save(garDept);
    }

    /**
     * Update.
     *
     * @param garDept the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarDept garDept) {
        deptDao.update(garDept);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void deleteById(Long id) {
        deptDao.deleteById(id);
    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Long> ids) {

    }

    /**
     * Query object t.
     *
     * @param id the id
     * @return the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarDept queryObject(Long id) {
        return deptDao.queryObject(id);
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
    public List<GarDept> queryObjects(String searchName, Integer page, Integer limit) {
        return null;
    }

    /**
     * Query total int.
     *
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal() {
        return 0;
    }


    /**
     * Query detp id list list.
     *
     * @param parentDeptId the parent dept id
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<Long> queryDetpIdList(Long parentDeptId) {
        return deptDao.queryDetpIdList(parentDeptId);
    }

    /**
     * Gets user dept list.
     * 获取该用户的部门列表
     *
     * @param userId the user id
     * @return the user dept list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarVMDept> getUserDeptList(Long userId) {
        GarUserDept userDept = userDeptService.queryObject(userId);
        String subDeptList = getSubDeptIdList(userDept.getDeptId());
        List<GarDept> depts = deptDao.queryDeptList(subDeptList, null, null, null);
        if (CollectionUtils.isEmpty(depts)) {
            return null;
        }
        List<GarVMDept> result = new ArrayList<>();
        for (GarDept dept : depts) {
            result.add(convertDeptToVMDept(dept));
        }
        return result;
    }

    /**
     * Gets vm dept by dept id.
     *
     * @param deptId the dept id
     * @return the vm dept by dept id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarVMDept getVMDeptByDeptId(Long deptId) {
        return convertDeptToVMDept(queryObject(deptId));
    }

    /**
     * Save vm dept.
     *
     * @param vmDept the vm dept
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void saveVMDept(GarVMDept vmDept) {
        if (vmDept.getDeptId() == null) {
            vmDept.setDeptId(IdGeneratorUtil.generateId());
        }

        //保存该部门下的所有用户
        saveUserInDept(vmDept);

        //保存该部门下的所有角色
        saveRoleInDept(vmDept);

        save(vmDept);
    }

    /**
     * Update vm dept.
     *
     * @param vmDept the vm dept
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void updateVMDept(GarVMDept vmDept) {
        //更新该部门下的所有用户,先删除，在插入
        userDeptService.deleteUserDeptByDeptId(vmDept.getDeptId());
        saveUserInDept(vmDept);

        //更新该部门下的所有角色,先删除，在插入
        roleDeptService.deleteRoleDeptByDeptId(vmDept.getDeptId());
        saveRoleInDept(vmDept);

        update(vmDept);
    }

    /**
     * Delete vm dept.
     *
     * @param deptId the dept id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteVMDept(Long deptId) {
        //删除该部门下的所有用户
        userDeptService.deleteUserDeptByDeptId(deptId);

        //删除该部门下的所有角色
        roleDeptService.deleteRoleDeptByDeptId(deptId);

        deleteById(deptId);
    }

    /**
     * Gets sub dept id list.
     *
     * @param deptId the dept id
     * @return the sub dept id list
     * @since garnet-core-be-fe 0.1.0
     */
    private String getSubDeptIdList(Long deptId) {
        //部门及子部门ID列表
        List<Long> deptIdList = new ArrayList<>();
        //获取子部门ID
        List<Long> subIdList = queryDetpIdList(deptId);
        getDeptTreeList(subIdList, deptIdList);
        //添加本部门
        deptIdList.add(deptId);
        return StringUtils.join(deptIdList, ",");
    }


    /**
     * Get dept tree list.
     *
     * @param subIdList  the sub id list
     * @param deptIdList the dept id list
     * @since garnet-core-be-fe 0.1.0
     */
    private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList) {
        for (Long deptId : subIdList) {
            List<Long> list = queryDetpIdList(deptId);
            if (list.size() > 0) {
                getDeptTreeList(list, deptIdList);
            }
            deptIdList.add(deptId);
        }
    }

    /**
     * Convert dept to vm dept gar vm dept.
     *
     * @param dept the dept
     * @return the gar vm dept
     * @since garnet-core-be-fe 0.1.0
     */
    private GarVMDept convertDeptToVMDept(GarDept dept) {
        GarVMDept vmDept = new GarVMDept();
        String appName = applicationService.queryObject(dept.getAppId()).getName();
        String tenantName = tenantService.queryObject(dept.getTenantId()).getName();

        vmDept.setAppName(appName);
        vmDept.setTenantName(tenantName);
        vmDept.setDeptId(dept.getDeptId());
        vmDept.setParentDeptId(dept.getParentDeptId());
        vmDept.setTenantId(dept.getTenantId());
        vmDept.setAppId(dept.getAppId());
        vmDept.setName(dept.getName());
        vmDept.setOrderNum(dept.getOrderNum());
        vmDept.setParentName(dept.getParentName());

        //获取该部门下的用户列表
        List<GarUser> userList = new ArrayList<>();
        List<GarUserDept> userDeptList = userDeptService.getUserDeptByDeptId(dept.getDeptId());
        if (!CollectionUtils.isEmpty(userDeptList)) {
            for (GarUserDept userDept : userDeptList) {
                userList.add(userService.queryObject(userDept.getUserId()));
            }
        }
        vmDept.setUserList(userList);

        //获取该部门下的角色列表
        List<GarRole> roleList = new ArrayList<>();
        List<GarRoleDept> roleDeptList = roleDeptService.getRoleDeptByDeptId(dept.getDeptId());
        if (!CollectionUtils.isEmpty(roleDeptList)) {
            for (GarRoleDept roleDept : roleDeptList) {
                roleList.add(roleService.queryObject(roleDept.getRoleId()));
            }
        }
        vmDept.setRoleList(roleList);
        return vmDept;
    }

    /**
     * Save user in dept.
     *
     * @param vmDept the vm dept
     * @since garnet-core-be-fe 0.1.0
     */
    private void saveUserInDept(GarVMDept vmDept) {
        List<Long> userIdList = GarnetRsUtil.parseStringToList(vmDept.getUserIds());
        if (!CollectionUtils.isEmpty(userIdList)) {
            for (Long userId : userIdList) {
                GarUserDept userDept = new GarUserDept();
                userDept.setUserId(userId);
                userDept.setDeptId(vmDept.getDeptId());
                userDeptService.save(userDept);
            }
        }
    }

    /**
     * Save role in dept.
     *
     * @param vmDept the vm dept
     * @since garnet-core-be-fe 0.1.0
     */
    private void saveRoleInDept(GarVMDept vmDept) {
        List<Long> roleIdList = GarnetRsUtil.parseStringToList(vmDept.getRoleIds());
        if (!CollectionUtils.isEmpty(roleIdList)) {
            for (Long roleId : roleIdList) {
                GarRoleDept roleDept = new GarRoleDept();
                roleDept.setRoleId(roleId);
                roleDept.setDeptId(vmDept.getDeptId());
                roleDeptService.save(roleDept);
            }
        }
    }
}
