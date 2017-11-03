/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.model.view.model;

import com.richstonedt.garnet.model.GarTenant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>GarVMTenant</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/11/3 14:44
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "前端页面租户信息")
public class GarVMTenant extends GarTenant implements Serializable {

    /**
     * The App name list.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用名称列表")
    private List<String> appNameList;

    /**
     * The App id list.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用ID列表")
    private List<Long> appIdList;

    /**
     * The App ids.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用ID，用‘，’隔开")
    private String appIds;

    /**
     * Return the AppNameList
     *
     * @return property value of appNameList
     * @since garnet-core-be-fe 0.1.0
     */
    public List<String> getAppNameList() {
        return appNameList;
    }

    /**
     * Set the AppNameList
     *
     * @param appNameList value to be assigned to property appNameList
     * @since garnet-core-be-fe 0.1.0
     */
    public void setAppNameList(List<String> appNameList) {
        this.appNameList = appNameList;
    }

    /**
     * Return the AppIdList
     *
     * @return property value of appIdList
     * @since garnet-core-be-fe 0.1.0
     */
    public List<Long> getAppIdList() {
        return appIdList;
    }

    /**
     * Set the AppIdList
     *
     * @param appIdList value to be assigned to property appIdList
     * @since garnet-core-be-fe 0.1.0
     */
    public void setAppIdList(List<Long> appIdList) {
        this.appIdList = appIdList;
    }

    /**
     * Return the AppIds
     *
     * @return property value of appIds
     * @since garnet-core-be-fe 0.1.0
     */
    public String getAppIds() {
        return appIds;
    }

    /**
     * Set the AppIds
     *
     * @param appIds value to be assigned to property appIds
     * @since garnet-core-be-fe 0.1.0
     */
    public void setAppIds(String appIds) {
        this.appIds = appIds;
    }
}
