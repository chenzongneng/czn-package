/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.model.view.model;

import com.richstonedt.garnet.model.GarApplication;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>GarVMApplication</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/11/3 14:36
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "前端页面应用信息")
public class GarVMApplication extends GarApplication implements Serializable {

    /**
     * The Tenant name list.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "租户名称列表")
    private List<String> tenantNameList;

    /**
     * The Tenant id list.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "租户ID列表")
    private List<Long> tenantIdList;

    /**
     * The Tenant ids.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "租户ID,用','隔开")
    private String tenantIds;

    /**
     * Return the TenantNameList
     *
     * @return property value of tenantNameList
     * @since garnet-core-be-fe 0.1.0
     */
    public List<String> getTenantNameList() {
        return tenantNameList;
    }

    /**
     * Set the TenantNameList
     *
     * @param tenantNameList value to be assigned to property tenantNameList
     * @since garnet-core-be-fe 0.1.0
     */
    public void setTenantNameList(List<String> tenantNameList) {
        this.tenantNameList = tenantNameList;
    }

    /**
     * Return the TenantIdList
     *
     * @return property value of tenantIdList
     * @since garnet-core-be-fe 0.1.0
     */
    public List<Long> getTenantIdList() {
        return tenantIdList;
    }

    /**
     * Set the TenantIdList
     *
     * @param tenantIdList value to be assigned to property tenantIdList
     * @since garnet-core-be-fe 0.1.0
     */
    public void setTenantIdList(List<Long> tenantIdList) {
        this.tenantIdList = tenantIdList;
    }

    /**
     * Return the TenantIds
     *
     * @return property value of tenantIds
     * @since garnet-core-be-fe 0.1.0
     */
    public String getTenantIds() {
        return tenantIds;
    }

    /**
     * Set the TenantIds
     *
     * @param tenantIds value to be assigned to property tenantIds
     * @since garnet-core-be-fe 0.1.0
     */
    public void setTenantIds(String tenantIds) {
        this.tenantIds = tenantIds;
    }

    /**
     * To String.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
