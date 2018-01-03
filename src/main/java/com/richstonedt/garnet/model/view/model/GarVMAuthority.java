/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.model.view.model;

import com.richstonedt.garnet.model.GarAuthority;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>GarVMAuthority</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 18:14
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
public class GarVMAuthority extends GarAuthority implements Serializable {

    @ApiModelProperty(value = "应用名称")
    private String applicationName;

    @ApiModelProperty(value = "菜单ID，用‘,’隔开")
    private String menuIds;

    @ApiModelProperty(value = "菜单ID列表")
    private List<Long> menuIdList;

    @ApiModelProperty(value = "菜单ID列表")
    private List<String> menuCodeList;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    public List<Long> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Long> menuIdList) {
        this.menuIdList = menuIdList;
    }

    public List<String> getMenuCodeList() {
        return menuCodeList;
    }

    public void setMenuCodeList(List<String> menuCodeList) {
        this.menuCodeList = menuCodeList;
    }
}
