/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.model.view.model;

import com.richstonedt.garnet.model.GarRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>GarVMRole</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/30 15:00
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "前端页面角色信息")
public class GarVMRole extends GarRole implements Serializable {

    /**
     * The Tenant name.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "租户名称")
    private String tenantName;

    /**
     * The App name.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用名称")
    private String appName;

    /**
     * The Dept name list.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "部门名称列表")
    private List<String> deptNameList;

    /**
     * The Dept id l list.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "部门ID列表")
    private List<Long> deptIdList;

    /**
     * The Dept ids.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "部门ID,以‘，’隔开")
    private String deptIds;

    // todo 权限属性


    /**
     * Return the TenantName
     *
     * @return property value of tenantName
     * @since garnet-core-be-fe 0.1.0
     */
    public String getTenantName() {
        return tenantName;
    }

    /**
     * Set the TenantName
     *
     * @param tenantName value to be assigned to property tenantName
     * @since garnet-core-be-fe 0.1.0
     */
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    /**
     * Return the AppName
     *
     * @return property value of appName
     * @since garnet-core-be-fe 0.1.0
     */
    public String getAppName() {
        return appName;
    }

    /**
     * Set the AppName
     *
     * @param appName value to be assigned to property appName
     * @since garnet-core-be-fe 0.1.0
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * Return the DeptNameList
     *
     * @return property value of deptNameList
     * @since garnet-core-be-fe 0.1.0
     */
    public List<String> getDeptNameList() {
        return deptNameList;
    }

    /**
     * Set the DeptNameList
     *
     * @param deptNameList value to be assigned to property deptNameList
     * @since garnet-core-be-fe 0.1.0
     */
    public void setDeptNameList(List<String> deptNameList) {
        this.deptNameList = deptNameList;
    }

    /**
     * Return the DeptIdLList
     *
     * @return property value of deptIdLList
     * @since garnet-core-be-fe 0.1.0
     */
    public List<Long> getDeptIdList() {
        return deptIdList;
    }

    /**
     * Set the DeptIdLList
     *
     * @param deptIdList value to be assigned to property deptIdLList
     * @since garnet-core-be-fe 0.1.0
     */
    public void setDeptIdList(List<Long> deptIdList) {
        this.deptIdList = deptIdList;
    }

    /**
     * Return the DeptIds
     *
     * @return property value of deptIds
     * @since garnet-core-be-fe 0.1.0
     */
    public String getDeptIds() {
        return deptIds;
    }

    /**
     * Set the DeptIds
     *
     * @param deptIds value to be assigned to property deptIds
     * @since garnet-core-be-fe 0.1.0
     */
    public void setDeptIds(String deptIds) {
        this.deptIds = deptIds;
    }

    /**
     * To String
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
