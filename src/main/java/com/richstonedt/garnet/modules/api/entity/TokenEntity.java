/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.api.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户Token
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:07
 * @since garnet-core-be-fe 1.0.0
 */
public class TokenEntity implements Serializable {

    /**
     * The constant serialVersionUID.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private static final long serialVersionUID = 1L;

    /**
     * The User id. 用户ID
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Long userId;

    /**
     * The Token.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private String token;

    /**
     * The Expire time. 过期时间
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Date expireTime;

    /**
     * The Update time. 更新时间
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Date updateTime;

    /**
     * 设置：用户ID
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取：用户ID
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置：token
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取：token
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置：过期时间
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 获取：过期时间
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public Date getExpireTime() {
        return expireTime;
    }

    /**
     * 设置：更新时间
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取：更新时间
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public Date getUpdateTime() {
        return updateTime;
    }
}
