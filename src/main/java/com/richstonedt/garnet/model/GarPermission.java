/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <b><code>GarAuthority</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 11:32
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@ApiModel(value = "权限")
public class GarPermission implements Serializable {

    /**
     * The Permission id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "权限ID")
    private Long permissionId;

    /**
     * The Application id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用ID")
    private Long applicationId;

    /**
     * The Name.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "具体名称")
    private String name;

    /**
     * The Wildcard.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "通配符")
    private String wildcard;

    /**
     * The Description.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "详细说明")
    private String description;

    /**
     * The Status.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * Gets permission id.
     *
     * @return the permission id
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getPermissionId() {
        return permissionId;
    }

    /**
     * Sets permission id.
     *
     * @param permissionId the permission id
     * @since garnet-core-be-fe 0.1.0
     */
    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
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
     * Gets wildcard.
     *
     * @return the wildcard
     * @since garnet-core-be-fe 0.1.0
     */
    public String getWildcard() {
        return wildcard;
    }

    /**
     * Sets wildcard.
     *
     * @param wildcard the wildcard
     * @since garnet-core-be-fe 0.1.0
     */
    public void setWildcard(String wildcard) {
        this.wildcard = wildcard;
    }
}
