package com.richstonedt.garnet.service;

import com.github.pagehelper.PageInfo;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.Tenant;
import com.richstonedt.garnet.model.criteria.TenantCriteria;
import com.richstonedt.garnet.model.parm.ApplicationParm;
import com.richstonedt.garnet.model.parm.TenantParm;
import com.richstonedt.garnet.model.view.TenantView;

public interface TenantService extends BaseService<Tenant, TenantCriteria, Long> {


    /**
     * 创建租户，添加applicatin或者user到关联表
     * @param tenantView
     */
    public Long insertTenant(TenantView tenantView);

    /**
     * 更新租户，更新applicatin或者user到关联表
     */
    public void updateTenant(TenantView tenantView);

    /**
     * 删除租户,并删除对应关联表的ID
     */
    public void deleteTenant(Long tenantId);

    /**
     * 根据条件搜索租户
     */
    public PageUtil queryTenantssByParms(TenantParm tenantParm);

    /**
     * 获取租户并且带上应用
     */
    public TenantView getTenantWithApplication(Long tenantId);

}