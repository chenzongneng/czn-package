/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * <b><code>GarApi</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/4 18:50
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@ApiModel(value = "API")
public class GarApi implements Serializable {

    /**
     * The Api id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "API ID")
    private Long apiId;

    /**
     * The Application id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用 ID")
    private Long applicationId;

    /**
     * The Parent id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "父 API ID")
    private Long parentId;

    /**
     * The Name.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "具体名称")
    private String name;

    /**
     * The Permission.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "Shiro的权限标识符")
    private String permission;

    /**
     * The Description.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "说明")
    private String description;

    /**
     * The Url.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "对应的链接")
    private String url;

    /**
     * The Method.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "方法")
    private String method;

    /**
     * The Status.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * Gets api id.
     *
     * @return the api id
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getApiId() {
        return apiId;
    }

    /**
     * Sets api id.
     *
     * @param apiId the api id
     * @since garnet-core-be-fe 0.1.0
     */
    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    /**
     * Gets name.
     *
     * @return the name
     * @since garnet-core-be-fe 0.1.0
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     * @since garnet-core-be-fe 0.1.0
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets permission.
     *
     * @return the permission
     * @since garnet-core-be-fe 0.1.0
     */
    public String getPermission() {
        return permission;
    }

    /**
     * Sets permission.
     *
     * @param permission the permission
     * @since garnet-core-be-fe 0.1.0
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * Gets description.
     *
     * @return the description
     * @since garnet-core-be-fe 0.1.0
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     * @since garnet-core-be-fe 0.1.0
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets status.
     *
     * @return the status
     * @since garnet-core-be-fe 0.1.0
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     * @since garnet-core-be-fe 0.1.0
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Gets url.
     *
     * @return the url
     * @since garnet-core-be-fe 0.1.0
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     * @since garnet-core-be-fe 0.1.0
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets application id.
     *
     * @return the application id
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getApplicationId() {
        return applicationId;
    }

    /**
     * Sets application id.
     *
     * @param applicationId the application id
     * @since garnet-core-be-fe 0.1.0
     */
    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * Gets parent id.
     *
     * @return the parent id
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * Sets parent id.
     *
     * @param parentId the parent id
     * @since garnet-core-be-fe 0.1.0
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * Gets method.
     *
     * @return the method
     * @since garnet-core-be-fe 0.1.0
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets method.
     *
     * @param method the method
     * @since garnet-core-be-fe 0.1.0
     */
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
