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
 * <b><code>GarDepartment</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/16 18:06
 *
 * @author PanXin
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "用户组(部门)信息")
public class GarDepartment implements Serializable {

    /**
     * The Department id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "部门ID")
    private Long departmentId;

    /**
     * The Parent department id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "父部门ID")
    private Long parentDepartmentId;

    /**
     * The Tenant id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * The Application id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用ID")
    private Long applicationId;

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
    private Integer orderNum;

    /**
     * The Parent name.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "父部门名称")
    private String parentName;

    /**
     * Return the DepartmentId
     *
     * @return property value of departmentId
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getDepartmentId() {
        return departmentId;
    }

    /**
     * Set the DepartmentId
     *
     * @param departmentId value to be assigned to property departmentId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * Return the ParentDepartmentId
     *
     * @return property value of parentDepartmentId
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getParentDepartmentId() {
        return parentDepartmentId;
    }

    /**
     * Set the ParentDepartmentId
     *
     * @param parentDepartmentId value to be assigned to property parentDepartmentId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setParentDepartmentId(Long parentDepartmentId) {
        this.parentDepartmentId = parentDepartmentId;
    }

    /**
     * Return the TenantId
     *
     * @return property value of tenantId
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getTenantId() {
        return tenantId;
    }

    /**
     * Set the TenantId
     *
     * @param tenantId value to be assigned to property tenantId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Return the ApplicationId
     *
     * @return property value of applicationId
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getApplicationId() {
        return applicationId;
    }

    /**
     * Set the ApplicationId
     *
     * @param applicationId value to be assigned to property applicationId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
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
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * Set the OrderNum
     *
     * @param orderNum value to be assigned to property orderNum
     * @since garnet-core-be-fe 0.1.0
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * Return the ParentName
     *
     * @return property value of parentName
     * @since garnet-core-be-fe 0.1.0
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * Set the ParentName
     *
     * @param parentName value to be assigned to property parentName
     * @since garnet-core-be-fe 0.1.0
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
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
