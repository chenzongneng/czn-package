package com.richstonedt.garnet.model.view;

import java.util.List;

public class SySMenuJsonObject {

    private Integer menuId;

    private Integer parentId;

    private String parentName;

    private String name;

    private String url;

    private Integer type;

    private String icon;

    private String code;

    private Integer orderNum;

    private String open;

    private List<SySMenuJsonObject> list;


    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public List<SySMenuJsonObject> getList() {
        return list;
    }

    public void setList(List<SySMenuJsonObject> list) {
        this.list = list;
    }

    public Integer getMenuId() {

        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}
