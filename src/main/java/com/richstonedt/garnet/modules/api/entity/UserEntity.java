/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.api.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:06
 * @since garnet-core-be-fe 1.0.0
 */
public class UserEntity implements Serializable {

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
     * The Username. 用户名
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private String username;

    /**
     * The Mobile.  手机号
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private String mobile;

    /**
     * The Password.  密码
     *
     * @since garnet-core-be-fe 1.0.0
     */
    transient private String password;

    /**
     * The Create time. 创建时间
     */
    private Date createTime;

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
     * 设置：用户名
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取：用户名
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置：手机号
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取：手机号
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置：密码
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取：密码
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置：创建时间
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：创建时间
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public Date getCreateTime() {
        return createTime;
    }
}
