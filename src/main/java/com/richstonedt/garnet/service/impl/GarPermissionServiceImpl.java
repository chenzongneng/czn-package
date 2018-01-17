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

    @Autowired
    private GarPermissionDao permissionDao;

//    @Autowired
//    private GarPermissionResourceDao permissionResourceDao;

    @Autowired
    private GarApplicationDao applicationDao;

    @Autowired
    private GarRolePermissionDao rolePermissionDao;

    private BeanCopier entityToVMCopier = BeanCopier.create(GarPermission.class, GarVMPermission.class,
            false);

    @Override
    public void save(GarPermission garPermission) {
        permissionDao.save(garPermission);
    }

    @Override
    public void update(GarPermission garPermission) {
        permissionDao.update(garPermission);
    }

    @Override
    public void deleteById(Long id) {
        rolePermissionDao.deleteByPermissionId(id);
        permissionDao.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        rolePermissionDao.deleteBatchByPermissionIds(ids);
        permissionDao.deleteBatch(ids);
    }

    @Override
    public GarPermission queryObject(Long id) {
        return permissionDao.queryObject(id);
    }

    @Override
    public List<GarPermission> queryObjects(Map<String, Object> params) {
        return permissionDao.queryObjects(params);
    }

    @Override
    public int queryTotal(Map<String, Object> params) {
        return permissionDao.queryTotal(params);
    }

    @Override
    public List<GarVMPermission> queryPermissionList(Map<String, Object> params) {
        List<GarPermission> authorities = permissionDao.queryObjects(params);
        List<GarVMPermission> result = new ArrayList<>();
        for (GarPermission permission : authorities) {
            result.add(convertPermissionToVmPermission(permission));
        }
        return result;
    }

    @Override
    public void savePermission(GarVMPermission garVMPermission) {
        permissionDao.save(garVMPermission);
//        savePermissionResource(garVMPermission);
    }

    @Override
    public GarVMPermission searchPermission(Long permissionId) {
        GarPermission permission = permissionDao.queryObject(permissionId);
        return convertPermissionToVmPermission(permission);
    }

    @Override
    public void updatePermission(GarVMPermission garVMPermission) {
        update(garVMPermission);
//        permissionResourceDao.deleteByPermissionId(garVMPermission.getPermissionId());
//        savePermissionResource(garVMPermission);
    }

    @Override
    public List<GarVMPermission> queryPermissionListByApplicationId(Long applicationId) {
        List<GarPermission> permissionList = permissionDao.getPermissionListByApplicationId(applicationId);
        List<GarVMPermission> result = new ArrayList<>();
        for (GarPermission permission : permissionList) {
            result.add(convertPermissionToVmPermission(permission));
        }
        return result;
    }

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
