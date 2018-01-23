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
 * <b><code>GarResource</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 11:32
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@ApiModel(value = "资源")
public class GarResource implements Serializable {

    /**
     * The Resource id.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "资源ID")
    private Long resourceId;

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
    @ApiModelProperty(value = "资源名称")
    private String name;

    /**
     * The Description.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "说明")
    private String description;

    /**
     * The Code.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "资源标识")
    private String code;

    /**
     * The Parent code.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "父资源标识")
    private String parentCode;

    /**
     * The Path.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "路径标识")
    private String path;

    /**
     * The Status.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * Gets resource id.
     *
     * @return the resource id
     * @since garnet-core-be-fe 0.1.0
     */
    public Long getResourceId() {
        return resourceId;
    }

    /**
     * Sets resource id.
     *
     * @param resourceId the resource id
     * @since garnet-core-be-fe 0.1.0
     */
    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
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
     * Gets code.
     *
     * @return the code
     * @since garnet-core-be-fe 0.1.0
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     * @since garnet-core-be-fe 0.1.0
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets parent code.
     *
     * @return the parent code
     * @since garnet-core-be-fe 0.1.0
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * Sets parent code.
     *
     * @param parentCode the parent code
     * @since garnet-core-be-fe 0.1.0
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
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
     * Gets path.
     *
     * @return the path
     * @since garnet-core-be-fe 0.1.0
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets path.
     *
     * @param path the path
     * @since garnet-core-be-fe 0.1.0
     */
    public void setPath(String path) {
        this.path = path;
    }
}
