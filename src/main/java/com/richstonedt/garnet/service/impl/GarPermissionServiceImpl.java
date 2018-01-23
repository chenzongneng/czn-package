/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarApplicationDao;
import com.richstonedt.garnet.dao.GarPermissionDao;
import com.richstonedt.garnet.dao.GarRolePermissionDao;
import com.richstonedt.garnet.model.GarApplication;
import com.richstonedt.garnet.model.GarPermission;
import com.richstonedt.garnet.model.view.model.GarVMPermission;
import com.richstonedt.garnet.service.GarPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b><code>GarPermissionServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 11:44
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Service
public class GarPermissionServiceImpl implements GarPermissionService {

    /**
     * The Permission dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarPermissionDao permissionDao;

    /**
     * The Application dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarApplicationDao applicationDao;

    /**
     * The Role permission dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarRolePermissionDao rolePermissionDao;

    /**
     * The Entity to vm copier.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private BeanCopier entityToVMCopier = BeanCopier.create(GarPermission.class, GarVMPermission.class,
            false);

    /**
     * Save.
     *
     * @param garPermission the gar permission
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarPermission garPermission) {
        permissionDao.save(garPermission);
    }

    /**
     * Update.
     *
     * @param garPermission the gar permission
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarPermission garPermission) {
        permissionDao.update(garPermission);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteById(Long id) {
        rolePermissionDao.deleteByPermissionId(id);
        permissionDao.deleteById(id);
    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Long> ids) {
        rolePermissionDao.deleteBatchByPermissionIds(ids);
        permissionDao.deleteBatch(ids);
    }

    /**
     * Query object gar permission.
     *
     * @param id the id
     * @return the gar permission
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarPermission queryObject(Long id) {
        return permissionDao.queryObject(id);
    }

    /**
     * Query objects list.
     *
     * @param params the params
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarPermission> queryObjects(Map<String, Object> params) {
        return permissionDao.queryObjects(params);
    }

    /**
     * Query total int.
     *
     * @param params the params
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal(Map<String, Object> params) {
        return permissionDao.queryTotal(params);
    }

    /**
     * Query permission list list.
     *
     * @param params the params
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarVMPermission> queryPermissionList(Map<String, Object> params) {
        List<GarPermission> authorities = permissionDao.queryObjects(params);
        List<GarVMPermission> result = new ArrayList<>();
        for (GarPermission permission : authorities) {
            result.add(convertPermissionToVmPermission(permission));
        }
        return result;
    }

    /**
     * Save permission.
     *
     * @param garVMPermission the gar vm permission
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void savePermission(GarVMPermission garVMPermission) {
        permissionDao.save(garVMPermission);
//        savePermissionResource(garVMPermission);
    }

    /**
     * Search permission gar vm permission.
     *
     * @param permissionId the permission id
     * @return the gar vm permission
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarVMPermission searchPermission(Long permissionId) {
        GarPermission permission = permissionDao.queryObject(permissionId);
        return convertPermissionToVmPermission(permission);
    }

    /**
     * Update permission.
     *
     * @param garVMPermission the gar vm permission
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void updatePermission(GarVMPermission garVMPermission) {
        update(garVMPermission);
//        permissionResourceDao.deleteByPermissionId(garVMPermission.getPermissionId());
//        savePermissionResource(garVMPermission);
    }

    /**
     * Query permission list by application id list.
     *
     * @param applicationId the application id
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarVMPermission> queryPermissionListByApplicationId(Long applicationId) {
        List<GarPermission> permissionList = permissionDao.getPermissionListByApplicationId(applicationId);
        List<GarVMPermission> result = new ArrayList<>();
        for (GarPermission permission : permissionList) {
            result.add(convertPermissionToVmPermission(permission));
        }
        return result;
    }

    /**
     * Convert permission to vm permission gar vm permission.
     *
     * @param garPermission the gar permission
     * @return the gar vm permission
     * @since garnet-core-be-fe 0.1.0
     */
    private GarVMPermission convertPermissionToVmPermission(GarPermission garPermission) {
        GarVMPermission vmPermission = new GarVMPermission();
        entityToVMCopier.copy(garPermission, vmPermission, null);
        GarApplication application = applicationDao.queryObject(garPermission.getApplicationId());
        vmPermission.setApplicationName(application.getName());
//        List<Long> menuId = permissionResourceDao.getResourceIdByPermissionId(garPermission.getPermissionId());
//        vmPermission.setResourceIdList(menuId);

        return vmPermission;
    }

//    private void savePermissionResource(GarVMPermission vmPermission) {
//        List<Long> menuIds = GarnetRsUtil.parseStringToList(vmPermission.getResourceIds());
//        for (Long menuId : menuIds) {
//            permissionResourceDao.save(vmPermission.getPermissionId(), menuId);
//        }
//    }
}
