package com.richstonedt.garnet.service;

import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Group;
import com.richstonedt.garnet.model.Tenant;
import com.richstonedt.garnet.model.criteria.TenantCriteria;
import com.richstonedt.garnet.model.parm.TenantParm;
import com.richstonedt.garnet.model.view.TenantView;

import java.util.List;

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

    /**
     * 当用户点击删除时，将其状态设为禁用
     * @param tenant
     */
    public void updateStatusById(Tenant tenant);

    List<Long> getApplicationIs();

    /**
     * 根据tenantId查询 该租户关联的用户
     * @param tenantId
     * @return
     */
    List<String> getRelatedUserNamesByTenantId(Long tenantId);

}