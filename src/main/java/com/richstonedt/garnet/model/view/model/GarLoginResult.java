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
 * <b><code>GarLoginResult</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/18 14:44
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "用户登录返回结果")
public class GarLoginResult implements Serializable {

    /**
     * The Login status.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "登录状态")
    private String loginStatus;

    /**
     * The Message.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "返回信息")
    private String message;

    /**
     * The Garnet token.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "garnet Token")
    private String garnetToken;

    /**
     * The User token.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "用户信息token")
    private String userToken;

    /**
     * Return the LoginStatus
     *
     * @return property value of loginStatus
     * @since garnet-core-be-fe 0.1.0
     */
    public String getLoginStatus() {
        return loginStatus;
    }

    /**
     * Set the LoginStatus
     *
     * @param loginStatus value to be assigned to property loginStatus
     * @since garnet-core-be-fe 0.1.0
     */
    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    /**
     * Return the Message
     *
     * @return property value of message
     * @since garnet-core-be-fe 0.1.0
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the Message
     *
     * @param message value to be assigned to property message
     * @since garnet-core-be-fe 0.1.0
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Return the GarnetToken
     *
     * @return property value of garnetToken
     * @since garnet-core-be-fe 0.1.0
     */
    public String getGarnetToken() {
        return garnetToken;
    }

    /**
     * Set the GarnetToken
     *
     * @param garnetToken value to be assigned to property garnetToken
     * @since garnet-core-be-fe 0.1.0
     */
    public void setGarnetToken(String garnetToken) {
        this.garnetToken = garnetToken;
    }

    /**
     * Return the UserToken
     *
     * @return property value of userToken
     * @since garnet-core-be-fe 0.1.0
     */
    public String getUserToken() {
        return userToken;
    }

    /**
     * Set the UserToken
     *
     * @param userToken value to be assigned to property userToken
     * @since garnet-core-be-fe 0.1.0
     */
    public void setUserToken(String userToken) {
        this.userToken = userToken;
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
