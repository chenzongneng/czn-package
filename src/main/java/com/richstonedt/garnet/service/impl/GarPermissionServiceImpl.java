/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.utils.ClassUtil;
import com.richstonedt.garnet.dao.GarPermissionDao;
import com.richstonedt.garnet.model.GarPermission;
import com.richstonedt.garnet.model.view.model.GarVmPermission;
import com.richstonedt.garnet.service.GarPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <b><code>GarPermissionServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 11:24
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Service
public class GarPermissionServiceImpl implements GarPermissionService {

    private BeanCopier entityToVMCopier = BeanCopier.create(GarPermission.class, GarVmPermission.class,
            false);

    @Autowired
    private GarPermissionDao permissionDao;

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
    public List<GarVmPermission> queryPermissionList(String searchName, Integer page, Integer limit) {
        int offset = (page - 1) * limit;
        List<GarVmPermission> vmPermissions = new ArrayList<>();
        List<GarPermission> permissions = permissionDao.queryObjects(searchName,limit,offset);
        for (GarPermission permission : permissions) {
            vmPermissions.add(convertPermissionToVMPermission(permission));
        }
        return vmPermissions;
    }

    private GarVmPermission convertPermissionToVMPermission(GarPermission permission) {
        GarVmPermission vmPermission = new GarVmPermission();
        entityToVMCopier.copy(permission,vmPermission,null);
        return vmPermission;
    }

    @Override
    public Set<String> getPermissionsByIds(Set<Long> ids) {
        return permissionDao.getPermissionsByIds(ids);
    }

    @Override
    public void importPermissionFromAnnotation(Class controllerClass,Long applicationId) {
        List<Class<?>> classList = ClassUtil.getClassListFromPackage(controllerClass);
        if (!CollectionUtils.isEmpty(classList)) {
            for (Class<?> clazz : classList) {
                this.getPermissionsByAnnotation(clazz,applicationId);
            }
        }
    }

    @Override
    public List<GarVmPermission> queryPermissionListByApplicationId(Long applicationId) {
        List<GarPermission> permissionList = permissionDao.getPermissionByApplicationIdAndStatus(applicationId, 1);
        List<GarVmPermission> vmPermissionList = new ArrayList<>();
        for (GarPermission garPermission:permissionList) {
            GarVmPermission vmPermission = convertPermissionToVMPermission(garPermission);
            vmPermissionList.add(vmPermission);
        }
        return vmPermissionList;
    }

    private void getPermissionsByAnnotation(Class<?> clazz,Long applicationId) {
        List<GarPermission> permissions = new ArrayList<>();
        Method[] methods = clazz.getMethods();
        String baseUrl = "";
        String api = "";
        RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
        if (!ObjectUtils.isEmpty(requestMapping)) {
            baseUrl = requestMapping.value()[0];
        }
        Api apiAnnotation = clazz.getAnnotation(Api.class);
        if (!ObjectUtils.isEmpty(apiAnnotation) && !ObjectUtils.isEmpty(apiAnnotation.tags())) {
            api = apiAnnotation.tags()[0];
        }

        GarPermission parentPermission = new GarPermission();
        parentPermission.setApplicationId(1L);
        parentPermission.setName(api);
        parentPermission.setStatus(1);
        saveOrUpdatePermission(parentPermission);

        if (!ObjectUtils.isEmpty(methods)) {
            for (Method method : methods) {
                RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
                if (!ObjectUtils.isEmpty(requiresPermissions)) {
                    GarPermission permission = new GarPermission();
                    ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
                    RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
                    permission.setApplicationId(applicationId);
                    permission.setParentId(parentPermission.getPermissionId());
                    permission.setPermission(requiresPermissions.value()[0]);
                    permission.setName(apiOperation.value());
                    permission.setDescription(apiOperation.notes());
                    permission.setUrl(baseUrl + methodMapping.value()[0]);
                    permission.setMethod(methodMapping.method()[0].toString());
                    permission.setStatus(1);
                    permissions.add(permission);
                }
            }
        }
        updatePermissions(permissions);
    }

    private void updatePermissions(List<GarPermission> permissions) {

        for (GarPermission permission : permissions) {
          saveOrUpdatePermission(permission);
        }

    }

    private void saveOrUpdatePermission(GarPermission permission) {
        List<GarPermission> dbPermissions = permissionDao.getPermissionByPermission(permission.getPermission());
        if (CollectionUtils.isEmpty(dbPermissions)) {
            permissionDao.save(permission);
        } else {
            permission.setPermissionId(dbPermissions.get(0).getPermissionId());
            permissionDao.update(permission);
        }
    }

}
