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

    @ApiModelProperty(value = "资源ID")
    private Long resourceId;

    @ApiModelProperty(value = "应用ID")
    private Long applicationId;

    @ApiModelProperty(value = "资源名称")
    private String name;

    @ApiModelProperty(value = "说明")
    private String description;

    @ApiModelProperty(value = "资源标识")
    private String code;

    @ApiModelProperty(value = "父资源标识")
    private String parentCode;

    @ApiModelProperty(value = "路径标识")
    private String path;

    @ApiModelProperty(value = "状态")
    private Integer status;

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
