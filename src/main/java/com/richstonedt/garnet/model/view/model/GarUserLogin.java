/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.model.view.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * <b><code>GarUserLogin</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/18 12:18
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "用户输入登录信息")
public class GarUserLogin implements Serializable {

    /**
     * The UserName.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * The Password.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * The Captcha.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "验证码")
    private String captcha;

    /**
     * The Now time.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "获取验证码时间")
    private String nowTime;

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
     * Return the Captcha
     *
     * @return property value of captcha
     * @since garnet-core-be-fe 0.1.0
     */
    public String getCaptcha() {
        return captcha;
    }

    /**
     * Set the Captcha
     *
     * @param captcha value to be assigned to property captcha
     * @since garnet-core-be-fe 0.1.0
     */
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    /**
     * Return the NowTime
     *
     * @return property value of nowTime
     * @since garnet-core-be-fe 0.1.0
     */
    public String getNowTime() {
        return nowTime;
    }

    /**
     * Set the NowTime
     *
     * @param nowTime value to be assigned to property nowTime
     * @since garnet-core-be-fe 0.1.0
     */
    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
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
