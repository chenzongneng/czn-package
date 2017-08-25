/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.authority.entity.viewModel;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>UserRoles</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/8/25 17:56
 *
 * @author Sun Jinpeng
 * @version 1.0.0
 * @since garnet-core-be-fe 1.0.0
 */
public class UserRoles implements Serializable{

    /**
     * The User id.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Integer userId;

    /**
     * The User name.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private String userName;

    /**
     * The Roles.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private List<String> roles;

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
     * Return the UserName
     *
     * @return property value of userName
     * @since garnet-core-be-fe 1.0.0
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the UserName
     *
     * @param userName value to be assigned to property userName
     * @since garnet-core-be-fe 1.0.0
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Return the Roles
     *
     * @return property value of roles
     * @since garnet-core-be-fe 1.0.0
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * Set the Roles
     *
     * @param roles value to be assigned to property roles
     * @since garnet-core-be-fe 1.0.0
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    /**
     * toString
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
