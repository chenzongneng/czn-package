/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * <b><code>GarUser</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/16 17:50
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "用户信息")
public class GarUser implements Serializable {

    /**
     * The User id.
     * 
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;

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
     * The Username.
     * 
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "账号")
    private String userName;

    /**
     * The Password.
     * 
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * The Username.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "用户姓名")
    private String name;

    /**
     * The Email.
     * 
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * The Mobile.
     * 
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "手机号")
    private String mobile;

    /**
     * The Status.
     * 
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "状态  0：禁用   1：正常")
    private Integer status;

    /**
     * The Create time.
     * 
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * Return the UserId
     *
     * @return property value of userId
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Set the UserId
     *
     * @param userId value to be assigned to property userId
     * @since garnet-core-be-fe 0.1.0
     */
    public void setUserId(Long userId) {
        this.userId = userId;
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
     * Return the UserName
     *
     * @return property value of userName
     * @since garnet-core-be-fe 0.1.0
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the UserName
     *
     * @param userName value to be assigned to property userName
     * @since garnet-core-be-fe 0.1.0
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Return the Password
     *
     * @return property value of password
     * @since garnet-core-be-fe 0.1.0
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the Password
     *
     * @param password value to be assigned to property password
     * @since garnet-core-be-fe 0.1.0
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return the Email
     *
     * @return property value of email
     * @since garnet-core-be-fe 0.1.0
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the Email
     *
     * @param email value to be assigned to property email
     * @since garnet-core-be-fe 0.1.0
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Return the Mobile
     *
     * @return property value of mobile
     * @since garnet-core-be-fe 0.1.0
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Set the Mobile
     *
     * @param mobile value to be assigned to property mobile
     * @since garnet-core-be-fe 0.1.0
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Return the Status
     *
     * @return property value of status
     * @since garnet-core-be-fe 0.1.0
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Set the Status
     *
     * @param status value to be assigned to property status
     * @since garnet-core-be-fe 0.1.0
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Return the CreateTime
     *
     * @return property value of createTime
     * @since garnet-core-be-fe 0.1.0
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
