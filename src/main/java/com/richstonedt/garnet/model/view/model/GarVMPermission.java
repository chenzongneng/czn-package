/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.model.view.model;

import com.richstonedt.garnet.model.GarPermission;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>GarVMPermission</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 18:14
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
public class GarVMPermission extends GarPermission implements Serializable {

    /**
     * The Application name.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用名称")
    private String applicationName;

    /**
     * The Menu ids.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "菜单ID，用‘,’隔开")
    private String menuIds;

    /**
     * The Menu id list.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "菜单ID列表")
    private List<Long> menuIdList;

    /**
     * The Menu code list.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "菜单ID列表")
    private List<String> menuCodeList;

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
     * Gets menu ids.
     *
     * @return the menu ids
     * @since garnet-core-be-fe 0.1.0
     */
    public String getMenuIds() {
        return menuIds;
    }

    /**
     * Sets menu ids.
     *
     * @param menuIds the menu ids
     * @since garnet-core-be-fe 0.1.0
     */
    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    /**
     * Gets menu id list.
     *
     * @return the menu id list
     * @since garnet-core-be-fe 0.1.0
     */
    public List<Long> getMenuIdList() {
        return menuIdList;
    }

    /**
     * Sets menu id list.
     *
     * @param menuIdList the menu id list
     * @since garnet-core-be-fe 0.1.0
     */
    public void setMenuIdList(List<Long> menuIdList) {
        this.menuIdList = menuIdList;
    }

    /**
     * Gets menu code list.
     *
     * @return the menu code list
     * @since garnet-core-be-fe 0.1.0
     */
    public List<String> getMenuCodeList() {
        return menuCodeList;
    }

    /**
     * Sets menu code list.
     *
     * @param menuCodeList the menu code list
     * @since garnet-core-be-fe 0.1.0
     */
    public void setMenuCodeList(List<String> menuCodeList) {
        this.menuCodeList = menuCodeList;
    }
}
