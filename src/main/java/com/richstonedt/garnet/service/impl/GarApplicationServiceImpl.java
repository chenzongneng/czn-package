/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.config.GarnetServiceException;
import com.richstonedt.garnet.dao.GarApplicationDao;
import com.richstonedt.garnet.model.GarApplication;
import com.richstonedt.garnet.model.GarApplicationTenant;
import com.richstonedt.garnet.model.GarTenant;
import com.richstonedt.garnet.model.view.model.GarVMApplication;
import com.richstonedt.garnet.service.GarAppTenantService;
import com.richstonedt.garnet.service.GarApplicationService;
import com.richstonedt.garnet.service.GarTenantService;
import com.richstonedt.garnet.common.utils.GarnetRsUtil;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <b><code>GarApplicationServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/17 10:29
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class GarApplicationServiceImpl implements GarApplicationService {

    /**
     * The Application dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarApplicationDao applicationDao;

    /**
     * The App tenant service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarAppTenantService appTenantService;

    /**
     * The Tenant service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarTenantService tenantService;

    /**
     * Save.
     *
     * @param garApplication the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarApplication garApplication) {
        Long applicationId = applicationDao.getApplicationIdByCode(garApplication.getCode());
        if (!ObjectUtils.isEmpty(applicationId)) {
            throw new GarnetServiceException("该应用标志已存在");
        }
        applicationDao.save(garApplication);
    }

    /**
     * Update.
     *
     * @param garApplication the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarApplication garApplication) {
        Long applicationId = applicationDao.getApplicationIdByCode(garApplication.getCode());
        if (!ObjectUtils.isEmpty(applicationId) && !applicationId.equals(garApplication.getApplicationId())) {
            throw new GarnetServiceException("该应用标志已存在");
        }
        applicationDao.update(garApplication);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void deleteById(Long id) {
        applicationDao.deleteById(id);
    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Long> ids) {
        applicationDao.deleteBatch(ids);
        appTenantService.deleteBatch(ids);
    }

    /**
     * Query object t.
     *
     * @param id the id
     * @return the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarApplication queryObject(Long id) {
        return applicationDao.queryObject(id);
    }

    /**
     * Query total int.
     *
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal(Map<String,Object> params) {
        return applicationDao.queryTotal(params);
    }

    /**
     * Query objects list.
     *
     * @param searchName the search name
     * @param limit      the limit
     * @param page       the offset
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarApplication> queryObjects(Map<String,Object> params) {
        return applicationDao.queryObjects(params);
    }

    /**
     * Save application.
     *
     * @param vmApplication the vm application
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void saveApplication(GarVMApplication vmApplication) {
//        if (vmApplication.getApplicationId() == null) {
//            vmApplication.setApplicationId(IdGeneratorUtil.generateId());
//        }
        save(vmApplication);
        saveApplicationTenant(vmApplication);
    }

    /**
     * Gets vm application by app id.
     *
     * @param appId the app id
     * @return the vm application by app id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarVMApplication getVmApplicationByAppId(Long appId) {
        return convertApplicationToVmApp(queryObject(appId));
    }

    /**
     * Query vm applications list.
     *
     * @param searchName the search name
     * @param page       the page
     * @param limit      the limit
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarVMApplication> queryVmApplications(Map<String,Object> params) {
        List<GarVMApplication> vmApplicationList = new ArrayList<>();
        List<GarApplication> applicationList = queryObjects(params);
        if (!CollectionUtils.isEmpty(applicationList)) {
            for (GarApplication application : applicationList) {
                vmApplicationList.add(convertApplicationToVmApp(application));
            }
        }
        return vmApplicationList;
    }

    /**
     * Update vm application.
     *
     * @param vmApplication the vm application
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void updateVmApplication(GarVMApplication vmApplication) {
        update(vmApplication);
        appTenantService.deleteById(vmApplication.getApplicationId());
        saveApplicationTenant(vmApplication);
    }

    /**
     * Convert application to vm app gar vm application.
     *
     * @param application the application
     * @return the gar vm application
     * @since garnet-core-be-fe 0.1.0
     */
    private GarVMApplication convertApplicationToVmApp(GarApplication application) {
        GarVMApplication vmApplication = new GarVMApplication();
        List<String> tenantNameList = new ArrayList<>();
        List<Long> tenantIdList = new ArrayList<>();
        List<GarApplicationTenant> appTenantList = appTenantService.getApplicationTenantByAppId(application.getApplicationId());
        if (!CollectionUtils.isEmpty(appTenantList)) {
            for (GarApplicationTenant appTenant : appTenantList) {
                GarTenant tenant = tenantService.queryObject(appTenant.getTenantId());
                if (!ObjectUtils.isEmpty(tenant)) {
                    tenantIdList.add(appTenant.getTenantId());
                    tenantNameList.add(tenant.getName());
                }
            }
        }
        vmApplication.setTenantIdList(tenantIdList);
        vmApplication.setTenantNameList(tenantNameList);
        vmApplication.setApplicationId(application.getApplicationId());
        vmApplication.setCompany(application.getCompany());
        vmApplication.setName(application.getName());
        vmApplication.setCode(application.getCode());
        vmApplication.setRemark(application.getRemark());
        vmApplication.setCreateTime(application.getCreateTime());
        return vmApplication;
    }

    /**
     * Save application tenant.
     *
     * @param vmApplication the vm application
     * @since garnet-core-be-fe 0.1.0
     */
    private void saveApplicationTenant(GarVMApplication vmApplication) {
        List<Long> tenantIdList = GarnetRsUtil.parseStringToList(vmApplication.getTenantIds());
        if (!CollectionUtils.isEmpty(tenantIdList)) {
            for (Long tenantId : tenantIdList) {
                GarApplicationTenant applicationTenant = new GarApplicationTenant();
                applicationTenant.setAppId(vmApplication.getApplicationId());
                applicationTenant.setTenantId(tenantId);
                appTenantService.save(applicationTenant);
            }
        }
    }

}
