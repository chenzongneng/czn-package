/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarApplicationDao;
import com.richstonedt.garnet.dao.GarPermissionDao;
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

    // 应用名称的map，缓存起来避免从实体转换为vm的时候都要去数据库查找应用名称
    private Map<Long,String> applicationNameMap = new HashMap<>();

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
        permissionDao.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        permissionDao.deleteBatch(ids);
    }

    @Override
    public GarPermission queryObject(Long id) {
        return permissionDao.queryObject(id);
    }

    @Override
    public List<GarPermission> queryObjects(String searchName, Integer page, Integer limit) {
        return permissionDao.queryObjects(searchName, page, limit);
    }

    @Override
    public int queryTotal() {
        return permissionDao.queryTotal();
    }

    @Override
    public List<GarVMPermission> queryPermissionList(String searchName, Integer page, Integer limit) {
        Integer offset = (page - 1) * limit;
        List<GarPermission> authorities = permissionDao.queryObjects(searchName,limit,offset);
        List<GarVMPermission> result = new ArrayList<>();
        for (GarPermission permission : authorities) {
            result.add(convertPermissionToVmPermission(permission));
        }
        // 使用几等将缓存清除，不然会存在内存中，如果有变动将可能无法同步。
        applicationNameMap.clear();
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
        entityToVMCopier.copy(garPermission,vmPermission,null);
        String applicationName = applicationNameMap.get(garPermission.getApplicationId());
        if (StringUtils.isEmpty(applicationName)) {
            GarApplication application = applicationDao.queryObject(garPermission.getApplicationId());
            applicationNameMap.put(garPermission.getApplicationId(), application.getName());
            vmPermission.setApplicationName(application.getName());
        } else {
            vmPermission.setApplicationName(applicationName);
        }
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
