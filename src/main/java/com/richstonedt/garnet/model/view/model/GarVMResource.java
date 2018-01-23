/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.model.view.model;

import com.richstonedt.garnet.model.GarResource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>GarVMResource</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 18:14
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@ApiModel(value = "前端页面资源信息")
public class GarVMResource extends GarResource implements Serializable {

    /**
     * The Application name.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用名称")
    private String applicationName;

    /**
     * The Parent name.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "父资源名称")
    private String parentName;

    /**
     * The Api ids.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "访问权限ID，用‘,’隔开")
    private String apiIds;

    /**
     * The Api id list.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "访问权限ID列表")
    private List<Long > apiIdList;

    /**
     * Gets application name.
     *
     * @return the application name
     * @since garnet-core-be-fe 0.1.0
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * Sets application name.
     *
     * @param applicationName the application name
     * @since garnet-core-be-fe 0.1.0
     */
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     * Gets parent name.
     *
     * @return the parent name
     * @since garnet-core-be-fe 0.1.0
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * Sets parent name.
     *
     * @param parentName the parent name
     * @since garnet-core-be-fe 0.1.0
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * Gets api ids.
     *
     * @return the api ids
     * @since garnet-core-be-fe 0.1.0
     */
    public String getApiIds() {
        return apiIds;
    }

    /**
     * Sets api ids.
     *
     * @param apiIds the api ids
     * @since garnet-core-be-fe 0.1.0
     */
    public void setApiIds(String apiIds) {
        this.apiIds = apiIds;
    }

    /**
     * Gets api id list.
     *
     * @return the api id list
     * @since garnet-core-be-fe 0.1.0
     */
    public List<Long> getApiIdList() {
        return apiIdList;
    }

    /**
     * Sets api id list.
     *
     * @param apiIdList the api id list
     * @since garnet-core-be-fe 0.1.0
     */
    public void setApiIdList(List<Long> apiIdList) {
        this.apiIdList = apiIdList;
    }
}
