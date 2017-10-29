/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.model.view.model;

import com.richstonedt.garnet.model.GarDept;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>GarVMDept</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/25 10:43
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet -core-be-fe 0.1.0
 */
@ApiModel(value = "页面部门信息")
public class GarVMDept extends GarDept implements Serializable {

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
     * The User list.
     */
    @ApiModelProperty(value = "用户名列表")
    private List<String> userNameList;

    @ApiModelProperty(value = "用户ID列表")
    private List<Long> userIdLList;

    /**
     * The Role list.
     */
    @ApiModelProperty(value = "角色名列表")
    private List<String> roleNameList;

    @ApiModelProperty(value = "角色ID列表")
    private List<Long> roleIdLList;

    /**
     * The User ids.
     */
    @ApiModelProperty(value = "用户ID,以‘，’隔开")
    private String userIds;

    /**
     * The Role ids.
     */
    @ApiModelProperty(value = "角色ID,以‘，’隔开")
    private String roleIds;

    /**
     * Return the TenantName
     *
     * @return property value of tenantName
     * @since garnet -core-be-fe 0.1.0
     */
    public String getTenantName() {
        return tenantName;
    }

    /**
     * Set the TenantName
     *
     * @param tenantName value to be assigned to property tenantName
     * @since garnet -core-be-fe 0.1.0
     */
    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    /**
     * Return the AppName
     *
     * @return property value of appName
     * @since garnet -core-be-fe 0.1.0
     */
    public String getAppName() {
        return appName;
    }

    /**
     * Set the AppName
     *
     * @param appName value to be assigned to property appName
     * @since garnet -core-be-fe 0.1.0
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * Return the UserIds
     *
     * @return property value of userIds
     * @since garnet -core-be-fe 0.1.0
     */
    public String getUserIds() {
        return userIds;
    }

    /**
     * Set the UserIds
     *
     * @param userIds value to be assigned to property userIds
     * @since garnet -core-be-fe 0.1.0
     */
    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    /**
     * Return the RoleIds
     *
     * @return property value of roleIds
     * @since garnet -core-be-fe 0.1.0
     */
    public String getRoleIds() {
        return roleIds;
    }

    /**
     * Set the RoleIds
     *
     * @param roleIds value to be assigned to property roleIds
     * @since garnet -core-be-fe 0.1.0
     */
    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public List<String> getUserNameList() {
        return userNameList;
    }

    public void setUserNameList(List<String> userNameList) {
        this.userNameList = userNameList;
    }

    public List<Long> getUserIdLList() {
        return userIdLList;
    }

    public void setUserIdLList(List<Long> userIdLList) {
        this.userIdLList = userIdLList;
    }

    public List<String> getRoleNameList() {
        return roleNameList;
    }

    public void setRoleNameList(List<String> roleNameList) {
        this.roleNameList = roleNameList;
    }

    public List<Long> getRoleIdLList() {
        return roleIdLList;
    }

    public void setRoleIdLList(List<Long> roleIdLList) {
        this.roleIdLList = roleIdLList;
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
