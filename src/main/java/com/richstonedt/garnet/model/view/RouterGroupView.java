package com.richstonedt.garnet.model.view;

import com.richstonedt.garnet.model.RouterGroup;

import java.util.List;

public class RouterGroupView {

    private RouterGroup routerGroup;

    private String applicationIds;

    private List<String> applicationNames;

    private List<String> appCodeList;

    private List<Long> applicationIdList;

    public List<Long> getApplicationIdList() {
        return applicationIdList;
    }

    public void setApplicationIdList(List<Long> applicationIdList) {
        this.applicationIdList = applicationIdList;
    }

    public List<String> getAppCodeList() {
        return appCodeList;
    }

    public void setAppCodeList(List<String> appCodeList) {
        this.appCodeList = appCodeList;
    }

    public String getApplicationIds() {
        return applicationIds;
    }

    public void setApplicationIds(String applicationIds) {
        this.applicationIds = applicationIds;
    }

    public List<String> getApplicationNames() {
        return applicationNames;
    }

    public void setApplicationNames(List<String> applicationNames) {
        this.applicationNames = applicationNames;
    }

    public RouterGroup getRouterGroup() {
        return routerGroup;
    }

    public void setRouterGroup(RouterGroup routerGroup) {
        this.routerGroup = routerGroup;
    }
}
