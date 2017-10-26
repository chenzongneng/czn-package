/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.model.view.model;

import com.richstonedt.garnet.model.GarUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>GarLoginResult</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/18 14:44
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "页面用户信息")
public class GarVMUser extends GarUser implements Serializable {

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
     * The Dept name.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "部门名称列表")
    private List<String> deptNameList;

    /**
     * The Dept id list.
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
    @ApiModelProperty(value = "前端传来的部门ID列表，用‘，’隔开")
    private String deptIds;

    /**
     * Gets tenant name.
     *
     * @return the tenant name
     * @since garnet-core-be-fe 0.1.0
     */
    public String getTenantName() {
        return tenantName;
    }

    /**
     * Sets tenant name.
     *
     * @param tenantName the tenant name
     * @since garnet-core-be-fe 0.1.0
     */
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    /**
     * Gets app name.
     *
     * @return the app name
     * @since garnet-core-be-fe 0.1.0
     */
    public String getAppName() {
        return appName;
    }

    /**
     * Sets app name.
     *
     * @param appName the app name
     * @since garnet-core-be-fe 0.1.0
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * Gets dept name.
     *
     * @return the dept name
     * @since garnet-core-be-fe 0.1.0
     */
    public List<String> getDeptNameList() {
        return deptNameList;
    }

    /**
     * Sets dept name.
     *
     * @param deptNameList the dept name
     * @since garnet-core-be-fe 0.1.0
     */
    public void setDeptNameList(List<String> deptNameList) {
        this.deptNameList = deptNameList;
    }

    /**
     * Return the DeptIdList
     *
     * @return property value of deptIdList
     * @since garnet-core-be-fe 0.1.0
     */
    public List<Long> getDeptIdList() {
        return deptIdList;
    }

    /**
     * Set the DeptIdList
     *
     * @param deptIdList value to be assigned to property deptIdList
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
