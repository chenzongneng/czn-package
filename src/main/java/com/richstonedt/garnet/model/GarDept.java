/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * <b><code>GarDept</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/16 18:06
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "用户组(部门)信息")
public class GarDept implements Serializable {

    /**
     * The Dept id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "部门ID")
    private Integer deptId;

    /**
     * The Parent dept id.
     */
    @ApiModelProperty(value = "父部门ID")
    private Integer parentDeptId;

    /**
     * The Tenant id.
     * 
     * @since garnet-core-be-fe 0.1.0
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "租户ID")
    private Integer tenantId;

    /**
     * The App id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用ID")
    private Integer appId;

    /**
     * The Name.
     * 
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "部门名称")
    private String name;

    /**
     * The Order num.
     * 
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "排序")
    private String orderNum;

    /**
     * Return the DeptId
     *
     * @return property value of deptId
     * @since garnet-core-be-fe 0.1.0
     */
    public Integer getDeptId() {
        return deptId;
    }

    /**
     * Set the DeptId
     *
     * @param deptId value to be assigned to property deptId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    /**
     * Return the ParentDeptId
     *
     * @return property value of parentDeptId
     * @since garnet-core-be-fe 0.1.0
     */
    public Integer getParentDeptId() {
        return parentDeptId;
    }

    /**
     * Set the ParentDeptId
     *
     * @param parentDeptId value to be assigned to property parentDeptId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setParentDeptId(Integer parentDeptId) {
        this.parentDeptId = parentDeptId;
    }

    /**
     * Return the TenantId
     *
     * @return property value of tenantId
     * @since garnet-core-be-fe 0.1.0
     */
    public Integer getTenantId() {
        return tenantId;
    }

    /**
     * Set the TenantId
     *
     * @param tenantId value to be assigned to property tenantId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Return the AppId
     *
     * @return property value of appId
     * @since garnet-core-be-fe 0.1.0
     */
    public Integer getAppId() {
        return appId;
    }

    /**
     * Set the AppId
     *
     * @param appId value to be assigned to property appId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    /**
     * Return the Name
     *
     * @return property value of name
     * @since garnet-core-be-fe 0.1.0
     */
    public String getName() {
        return name;
    }

    /**
     * Set the Name
     *
     * @param name value to be assigned to property name
     * @since garnet-core-be-fe 0.1.0
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the OrderNum
     *
     * @return property value of orderNum
     * @since garnet-core-be-fe 0.1.0
     */
    public String getOrderNum() {
        return orderNum;
    }

    /**
     * Set the OrderNum
     *
     * @param orderNum value to be assigned to property orderNum
     * @since garnet-core-be-fe 0.1.0
     */
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
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
