/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>GarSysMenu</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/16 17:37
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@ApiModel(value = "系统菜单")
public class GarSysMenu implements Serializable {

    /**
     * 菜单ID
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

    /**
     * 父菜单ID，一级菜单为0
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;

    /**
     * 父菜单名称
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @ApiModelProperty(value = "父菜单名称")
    private String parentName;

    /**
     * 菜单名称
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @ApiModelProperty(value = "菜单名称")
    private String name;

    /**
     * 菜单URL
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @ApiModelProperty(value = "菜单URL")
    private String url;

    /**
     * 类型     0：目录   1：菜单   2：按钮
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @ApiModelProperty(value = "类型")
    private Integer type;

    /**
     * 菜单图标
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "标志")
    private String code;

    /**
     * 排序
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    /**
     * ztree属性
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @ApiModelProperty(value = "ztree属性")
    private Boolean open;

    /**
     * The List.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @ApiModelProperty(value = "list")
    private List<?> list;

    /**
     * Sets menu id.
     *
     * @param menuId the menu id
     * @since garnet-core-be-fe 1.0.0
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * Gets menu id.
     *
     * @return the menu id
     * @since garnet-core-be-fe 1.0.0
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 设置：父菜单ID，一级菜单为0
     *
     * @param parentId 父菜单ID，一级菜单为0
     * @since garnet-core-be-fe 1.0.0
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取：父菜单ID，一级菜单为0
     *
     * @return Long
     * @since garnet-core-be-fe 1.0.0
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置：菜单名称
     *
     * @param name 菜单名称
     * @since garnet-core-be-fe 1.0.0
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：菜单名称
     *
     * @return String
     * @since garnet-core-be-fe 1.0.0
     */
    public String getName() {
        return name;
    }

    /**
     * 设置：菜单URL
     *
     * @param url 菜单URL
     * @since garnet-core-be-fe 1.0.0
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取：菜单URL
     *
     * @return String
     */
    public String getUrl() {
        return url;
    }

    /**
     * Gets type.
     *
     * @return the type
     * @since garnet-core-be-fe 1.0.0
     */
    public Integer getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     * @since garnet-core-be-fe 1.0.0
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 设置：菜单图标
     *
     * @param icon 菜单图标
     * @since garnet-core-be-fe 1.0.0
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * 获取：菜单图标
     *
     * @return String
     * @since garnet-core-be-fe 1.0.0
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置：排序
     *
     * @param orderNum 排序
     * @since garnet-core-be-fe 1.0.0
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 获取：排序
     *
     * @return Integer
     * @since garnet-core-be-fe 1.0.0
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * Gets list.
     *
     * @return the list
     * @since garnet-core-be-fe 1.0.0
     */
    public List<?> getList() {
        return list;
    }

    /**
     * Sets list.
     *
     * @param list the list
     * @since garnet-core-be-fe 1.0.0
     */
    public void setList(List<?> list) {
        this.list = list;
    }

    /**
     * Gets parent name.
     *
     * @return the parent name
     * @since garnet-core-be-fe 1.0.0
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * Sets parent name.
     *
     * @param parentName the parent name
     * @since garnet-core-be-fe 1.0.0
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * Gets open.
     *
     * @return the open
     * @since garnet-core-be-fe 1.0.0
     */
    public Boolean getOpen() {
        return open;
    }

    /**
     * Sets open.
     *
     * @param open the open
     * @since garnet-core-be-fe 1.0.0
     */
    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
