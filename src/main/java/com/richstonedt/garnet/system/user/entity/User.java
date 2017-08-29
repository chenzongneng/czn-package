/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.user.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * <b><code>User</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/8/29 14:51
 *
 * @author Sun Jinpeng
 * @version 1.0.0
 * @since garnet-core-be-fe 1.0.0
 */
public class User implements Serializable{

    /**
     * 用户ID
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Integer userId;

    /**
     * 用户名
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private String username;

    /**
     * 密码
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private String password;

    /**
     * 盐
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private String salt;

    /**
     * 邮箱
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private String email;

    /**
     * 手机号
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private String mobile;

    /**
     * 状态  0：禁用   1：正常
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Integer status;

    /**
     * 创建时间
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Date createTime;

    /**
     * 状态  0：不是   1：是
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Integer admin;

    /**
     * Return the UserId
     *
     * @return property value of userId
     * @since garnet-core-be-fe 1.0.0
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Set the UserId
     *
     * @param userId value to be assigned to property userId
     * @since garnet-core-be-fe 1.0.0
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Return the Username
     *
     * @return property value of username
     * @since garnet-core-be-fe 1.0.0
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the Username
     *
     * @param username value to be assigned to property username
     * @since garnet-core-be-fe 1.0.0
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Return the Password
     *
     * @return property value of password
     * @since garnet-core-be-fe 1.0.0
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the Password
     *
     * @param password value to be assigned to property password
     * @since garnet-core-be-fe 1.0.0
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return the Salt
     *
     * @return property value of salt
     * @since garnet-core-be-fe 1.0.0
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Set the Salt
     *
     * @param salt value to be assigned to property salt
     * @since garnet-core-be-fe 1.0.0
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * Return the Email
     *
     * @return property value of email
     * @since garnet-core-be-fe 1.0.0
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the Email
     *
     * @param email value to be assigned to property email
     * @since garnet-core-be-fe 1.0.0
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Return the Mobile
     *
     * @return property value of mobile
     * @since garnet-core-be-fe 1.0.0
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * Set the Mobile
     *
     * @param mobile value to be assigned to property mobile
     * @since garnet-core-be-fe 1.0.0
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * Return the Status
     *
     * @return property value of status
     * @since garnet-core-be-fe 1.0.0
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Set the Status
     *
     * @param status value to be assigned to property status
     * @since garnet-core-be-fe 1.0.0
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Return the CreateTime
     *
     * @return property value of createTime
     * @since garnet-core-be-fe 1.0.0
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Set the CreateTime
     *
     * @param createTime value to be assigned to property createTime
     * @since garnet-core-be-fe 1.0.0
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * Return the Admin
     *
     * @return property value of admin
     * @since garnet-core-be-fe 1.0.0
     */
    public Integer getAdmin() {
        return admin;
    }

    /**
     * Set the Admin
     *
     * @param admin value to be assigned to property admin
     * @since garnet-core-be-fe 1.0.0
     */
    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    /**
     * to String
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
