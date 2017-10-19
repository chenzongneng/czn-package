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
 * <b><code>GarToken</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/18 14:23
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "用户token")
public class GarToken implements Serializable {

    /**
     * The User id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    /**
     * The Token.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "token")
    private String token;

    /**
     * The Expire time.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "过期时间")
    private Date expireTime;

    /**
     * The Update time.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

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
     * Return the Token
     *
     * @return property value of token
     * @since garnet-core-be-fe 0.1.0
     */
    public String getToken() {
        return token;
    }

    /**
     * Set the Token
     *
     * @param token value to be assigned to property token
     * @since garnet-core-be-fe 0.1.0
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Return the ExpireTime
     *
     * @return property value of expireTime
     * @since garnet-core-be-fe 0.1.0
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * Set the ExpireTime
     *
     * @param expireTime value to be assigned to property expireTime
     * @since garnet-core-be-fe 0.1.0
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * Return the UpdateTime
     *
     * @return property value of updateTime
     * @since garnet-core-be-fe 0.1.0
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * Set the UpdateTime
     *
     * @param updateTime value to be assigned to property updateTime
     * @since garnet-core-be-fe 0.1.0
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
