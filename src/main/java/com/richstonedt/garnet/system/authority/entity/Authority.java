/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.authority.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * <b><code>Authority</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/8/25 17:52
 *
 * @author Sun Jinpeng
 * @version 1.0.0
 * @since garnet-core-be-fe 1.0.0
 */
public class Authority implements Serializable {

    /**
     * The User id.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Integer userId ;

    /**
     * The Role id.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Integer roleId;

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
     * Return the RoleId
     *
     * @return property value of roleId
     * @since garnet-core-be-fe 1.0.0
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * Set the RoleId
     *
     * @param roleId value to be assigned to property roleId
     * @since garnet-core-be-fe 1.0.0
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
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
