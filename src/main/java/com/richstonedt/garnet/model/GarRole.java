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
import java.util.Date;

/**
 * <b><code>GarRole</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/16 17:58
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "角色信息")
public class GarRole implements Serializable{

    /**
     * The Role id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    /**
     * The Tenant id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "租户ID")
    private Long tenantId;

    /**
     * The App id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用ID")
    private Long appId;

    /**
     * The Name.
     * 
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "角色名称")
    private String name;

    /**
     * The Remark.
     * 
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * The Create time.
     * 
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * Return the RoleId
     *
     * @return property value of roleId
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * Set the RoleId
     *
     * @param roleId value to be assigned to property roleId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
     * Return the AppId
     *
     * @return property value of appId
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getAppId() {
        return appId;
    }

    /**
     * Set the AppId
     *
     * @param appId value to be assigned to property appId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setAppId(Long appId) {
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
     * Return the Remark
     *
     * @return property value of remark
     * @since garnet-core-be-fe 0.1.0
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Set the Remark
     *
     * @param remark value to be assigned to property remark
     * @since garnet-core-be-fe 0.1.0
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * Return the CreateTime
     *
     * @return property value of createTime
     * @since garnet-core-be-fe 0.1.0
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Set the CreateTime
     *
     * @param createTime value to be assigned to property createTime
     * @since garnet-core-be-fe 0.1.0
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
