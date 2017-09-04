/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * <b><code>UserLoginEntity</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/9/4 14:30
 *
 * @author Sun Jinpeng
 * @version 1.0.0
 * @since garnet-core-be-fe 1.0.0
 */
public class UserLoginEntity implements Serializable{

    /**
     * The Username.
     * 
     * @since garnet-core-be-fe 1.0.0
     */
    private String username;
    
    /**
     * The Password.
     * 
     * @since garnet-core-be-fe 1.0.0
     */
    private String password;
    
    /**
     * The Captcha.
     * 
     * @since garnet-core-be-fe 1.0.0
     */
    private String captcha;
    
    /**
     * The Now time.
     * 
     * @since garnet-core-be-fe 1.0.0
     */
    private String nowTime;

    /**
     * Return the Username
     *
     * @return property value of username
     * @since @since garnet-core-be-fe 1.0.0
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the Username
     *
     * @param username value to be assigned to property username
     * @since @since garnet-core-be-fe 1.0.0
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Return the Password
     *
     * @return property value of password
     * @since @since garnet-core-be-fe 1.0.0
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the Password
     *
     * @param password value to be assigned to property password
     * @since @since garnet-core-be-fe 1.0.0
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return the Captcha
     *
     * @return property value of captcha
     * @since @since garnet-core-be-fe 1.0.0
     */
    public String getCaptcha() {
        return captcha;
    }

    /**
     * Set the Captcha
     *
     * @param captcha value to be assigned to property captcha
     * @since @since garnet-core-be-fe 1.0.0
     */
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    /**
     * Return the NowTime
     *
     * @return property value of nowTime
     * @since @since garnet-core-be-fe 1.0.0
     */
    public String getNowTime() {
        return nowTime;
    }

    /**
     * Set the NowTime
     *
     * @param nowTime value to be assigned to property nowTime
     * @since @since garnet-core-be-fe 1.0.0
     */
    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }

    /**
     * To  String
     *
     * @since @since garnet-core-be-fe 1.0.0
     */
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
