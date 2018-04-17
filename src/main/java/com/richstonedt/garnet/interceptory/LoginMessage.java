package com.richstonedt.garnet.interceptory;

import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.ResourceDynamicProperty;
import com.richstonedt.garnet.model.User;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class LoginMessage {

    private String message;

    private User user;

    private String loginStatus;

    @ApiModelProperty(value = "访问token")
    private String accessToken;

    @ApiModelProperty(value = "刷新token")
    private String refreshToken;

    private int code;

    @ApiModelProperty(value = "资源列表")
    private List<Resource> resourceList;

    @ApiModelProperty(value = "资源列表readonly组")
    private List<Resource> resourceListWithReadlyOnly;

    @ApiModelProperty(value = "资源列表edit组")
    private List<Resource> getResourceListWithEdit;

    @ApiModelProperty(value = "资源类型配置列表")
    private List<List<ResourceDynamicProperty>> resourceDynamicPropertyList;

    public List<Resource> getResourceListWithReadlyOnly() {
        return resourceListWithReadlyOnly;
    }

    public void setResourceListWithReadlyOnly(List<Resource> resourceListWithReadlyOnly) {
        this.resourceListWithReadlyOnly = resourceListWithReadlyOnly;
    }

    public List<Resource> getGetResourceListWithEdit() {
        return getResourceListWithEdit;
    }

    public void setGetResourceListWithEdit(List<Resource> getResourceListWithEdit) {
        this.getResourceListWithEdit = getResourceListWithEdit;
    }

    public List<List<ResourceDynamicProperty>> getResourceDynamicPropertyList() {
        return resourceDynamicPropertyList;
    }

    public void setResourceDynamicPropertyList(List<List<ResourceDynamicProperty>> resourceDynamicPropertyList) {
        this.resourceDynamicPropertyList = resourceDynamicPropertyList;
    }

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
