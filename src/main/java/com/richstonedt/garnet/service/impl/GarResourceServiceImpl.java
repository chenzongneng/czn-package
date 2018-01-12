/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.utils.GarnetRsUtil;
import com.richstonedt.garnet.dao.GarApplicationDao;
import com.richstonedt.garnet.dao.GarResourceDao;
import com.richstonedt.garnet.dao.GarResourceApiDao;
import com.richstonedt.garnet.model.GarApplication;
import com.richstonedt.garnet.model.GarResource;
import com.richstonedt.garnet.model.view.model.GarVMResource;
import com.richstonedt.garnet.service.GarResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <b><code>GarResourceServiceImpl</code></b>
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
public class GarResourceServiceImpl implements GarResourceService {

    @Autowired
    private GarResourceDao resourceDao;

    @Autowired
    private GarResourceApiDao resourceApiDao;

    @Autowired
    private GarApplicationDao applicationDao;

    private BeanCopier entityToVMCopier = BeanCopier.create(GarResource.class, GarVMResource.class,
            false);

    @Override
    public void save(GarResource garResource) {
        resourceDao.save(garResource);
    }

    @Override
    public void update(GarResource garResource) {
        resourceDao.update(garResource);
    }

    @Override
    public void deleteById(Long id) {
        resourceDao.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        resourceDao.deleteBatch(ids);
    }

    @Override
    public GarResource queryObject(Long id) {
        return resourceDao.queryObject(id);
    }

    @Override
    public List<GarResource> queryObjects(Map<String,Object> params) {
        return resourceDao.queryObjects(params);
    }

    @Override
    public int queryTotal(Map<String,Object> params) {
        return resourceDao.queryTotal(params);
    }

    @Override
    public List<GarVMResource> queryResourceListByParams(Map<String, Object> params) {
        List<GarResource> authorities = resourceDao.getResourcesByParams(params);
        List<GarVMResource> result = new ArrayList<>();
        for (GarResource resource : authorities) {
            result.add(convertResourceToVmResource(resource));
        }
        return result;
    }

    @Override
    public List<GarVMResource> queryResourceListByAppId(Long appId) {
        List<GarResource> resourceList = resourceDao.getResourcesByAppId(appId);
        List<GarVMResource> vmResourceList = new ArrayList<>();
        for (GarResource resource : resourceList) {
            GarVMResource vmResource = convertResourceToVmResource(resource);
            vmResourceList.add(vmResource);
        }
        return vmResourceList;
    }

    @Override
    public void saveResource(GarVMResource garVMResource) {
        resourceDao.save(garVMResource);
        saveResourceApi(garVMResource);
    }

    @Override
    public GarVMResource searchResource(Long resourceId) {
        GarResource resource = resourceDao.queryObject(resourceId);
        GarVMResource garVMResource = convertResourceToVmResource(resource);
        return garVMResource;
    }

    @Override
    public void updateResource(GarVMResource vmResource) {
        update(vmResource);
        resourceApiDao.deleteByResourceId(vmResource.getResourceId());
        saveResourceApi(vmResource);
    }

    @Override
    public int queryTotalResourceByParam(Map<String, Object> params) {
        return resourceDao.getTotalResourceByParam(params);
    }

    private GarVMResource convertResourceToVmResource(GarResource garResource) {
        GarVMResource vmResource = new GarVMResource();
        entityToVMCopier.copy(garResource, vmResource, null);
        vmResource.setParentName(resourceDao.getResourceNameByCode(garResource.getParentCode()));
        GarApplication application = applicationDao.queryObject(garResource.getApplicationId());
        vmResource.setApplicationName(application.getName());
        vmResource.setApiIdList(resourceApiDao.getApiIdsByResourceId(vmResource.getResourceId()));
        return vmResource;
    }

    private void saveResourceApi(GarVMResource vmResource) {
        List<Long> permissionIdList = GarnetRsUtil.parseStringToList(vmResource.getApiIds());
        for (Long permissionId : permissionIdList) {
            resourceApiDao.save(vmResource.getResourceId(), permissionId);
        }
    }

}
