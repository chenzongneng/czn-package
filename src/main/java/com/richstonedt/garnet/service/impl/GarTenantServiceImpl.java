/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarTenantDao;
import com.richstonedt.garnet.model.GarApplication;
import com.richstonedt.garnet.model.GarApplicationTenant;
import com.richstonedt.garnet.model.GarTenant;
import com.richstonedt.garnet.model.view.model.GarVMTenant;
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
import java.util.Date;
import java.util.List;

/**
 * <b><code>GarTenantServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/17 15:46
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class GarTenantServiceImpl implements GarTenantService {

    /**
     * The Tenant dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarTenantDao tenantDao;

    /**
     * The App tenant service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarAppTenantService appTenantService;

    /**
     * The Application service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarApplicationService applicationService;

    /**
     * Save.
     *
     * @param garTenant the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarTenant garTenant) {
        if (garTenant.getTenantId() == null) {
            garTenant.setTenantId(IdGeneratorUtil.generateId());
        }
        tenantDao.save(garTenant);
    }

    /**
     * Update.
     *
     * @param garTenant the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarTenant garTenant) {
        tenantDao.update(garTenant);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void deleteById(Long id) {
        tenantDao.deleteById(id);
    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Long> ids) {
        tenantDao.deleteBatch(ids);
        for (Long id : ids) {
            appTenantService.deleteApplicationTenantByTenantId(id);
        }
    }

    /**
     * Query object t.
     *
     * @param id the id
     * @return the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarTenant queryObject(Long id) {
        return tenantDao.queryObject(id);
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
    public List<GarTenant> queryObjects(String searchName, Integer page, Integer limit) {
        Integer offset = (page - 1) * limit;
        return tenantDao.queryObjects(searchName, limit, offset);
    }

    /**
     * Query total int.
     *
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal() {
        return tenantDao.queryTotal();
    }

    /**
     * Save tenant.
     *
     * @param vmTenant the vm tenant
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void saveTenant(GarVMTenant vmTenant) {
        if (vmTenant.getTenantId() == null) {
            vmTenant.setTenantId(IdGeneratorUtil.generateId());
        }
        vmTenant.setCreateTime(new Date());
        saveApplicationTenant(vmTenant);
        save(vmTenant);
    }

    /**
     * Save application tenant.
     *
     * @param vmTenant the vm tenant
     * @since garnet-core-be-fe 0.1.0
     */
    private void saveApplicationTenant(GarVMTenant vmTenant) {
        List<Long> appIds = GarnetRsUtil.parseStringToList(vmTenant.getAppIds());
        if (!CollectionUtils.isEmpty(appIds)) {
            for (Long appId : appIds) {
                GarApplicationTenant applicationTenant = new GarApplicationTenant();
                applicationTenant.setTenantId(vmTenant.getTenantId());
                applicationTenant.setAppId(appId);
                appTenantService.save(applicationTenant);
            }
        }
    }

    /**
     * Gets vm tenant by tenant id.
     *
     * @param tenantId the tenant id
     * @return the vm tenant by tenant id
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarVMTenant getVmTenantByTenantId(Long tenantId) {
        return convertTenantToVmTenant(queryObject(tenantId));
    }

    /**
     * Convert tenant to vm tenant gar vm tenant.
     *
     * @param tenant the tenant
     * @return the gar vm tenant
     * @since garnet-core-be-fe 0.1.0
     */
    private GarVMTenant convertTenantToVmTenant(GarTenant tenant) {
        GarVMTenant vmTenant = new GarVMTenant();
        List<String> appNameList = new ArrayList<>();
        List<Long> appIdList = new ArrayList<>();
        List<GarApplicationTenant> appTenantList = appTenantService.getApplicationTenantByTenantId(tenant.getTenantId());
        if (!CollectionUtils.isEmpty(appTenantList)) {
            for (GarApplicationTenant appTenant : appTenantList) {
                appIdList.add(appTenant.getAppId());
                GarApplication garApplication = applicationService.queryObject(appTenant.getAppId());
                if (!ObjectUtils.isEmpty(garApplication)) {
                    appNameList.add(garApplication.getName());
                }
            }
        }
        vmTenant.setAppIdList(appIdList);
        vmTenant.setAppNameList(appNameList);
        vmTenant.setTenantId(tenant.getTenantId());
        vmTenant.setName(tenant.getName());
        vmTenant.setRemark(tenant.getRemark());
        vmTenant.setCreateTime(tenant.getCreateTime());
        return vmTenant;
    }

    /**
     * Query vm tenants list.
     *
     * @param searchName the search name
     * @param page       the page
     * @param limit      the limit
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarVMTenant> queryVmTenants(String searchName, Integer page, Integer limit) {
        List<GarVMTenant> vmTenantList = new ArrayList<>();
        List<GarTenant> tenantList = queryObjects(searchName, page, limit);
        if (!CollectionUtils.isEmpty(tenantList)) {
            for (GarTenant tenant : tenantList) {
                vmTenantList.add(convertTenantToVmTenant(tenant));
            }
        }
        return vmTenantList;
    }

    /**
     * Update vm tenant.
     *
     * @param vmTenant the vm tenant
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void updateVmTenant(GarVMTenant vmTenant) {
        update(vmTenant);

        appTenantService.deleteApplicationTenantByTenantId(vmTenant.getTenantId());
        saveApplicationTenant(vmTenant);
    }
}
