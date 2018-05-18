package com.richstonedt.garnet.interceptory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.richstonedt.garnet.model.Resource;
import com.richstonedt.garnet.model.ResourceDynamicProperty;
import com.richstonedt.garnet.model.User;
import com.richstonedt.garnet.model.view.RefreshTokenResourceView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

@ApiModel(value = "登录或刷新token返回mode", description = "登录或刷新token返回mode")
public class LoginMessage {

    @ApiModelProperty(value = "登录返回信息")
    private String message;

    @ApiModelProperty(value = "登录用户")
    private User user;

    @ApiModelProperty(value = "登录状态")
    private String loginStatus;

    @ApiModelProperty(value = "")
    private int code;

    @JsonIgnore
    @ApiModelProperty(value = "登录用户关联的租户id")
    private List<Long> tenantIdList;

    @ApiModelProperty(value = "访问token")
    private String accessToken;

    @ApiModelProperty(value = "刷新token")
    private String refreshToken;

    @ApiModelProperty(value = "资源列表")
    private List<Resource> resourceList;

    @ApiModelProperty(value = "刷新token时获取的资源列表")
    private List<RefreshTokenResourceView> refreshTokenResourceList;

    @ApiModelProperty(value = "资源列表readonly组")
    private List<Resource> resourceListWithReadlyOnly;

    @ApiModelProperty(value = "资源列表edit组")
    private List<Resource> getResourceListWithEdit;

    @ApiModelProperty(value = "资源类型配置列表")
    private List<List<ResourceDynamicProperty>> resourceDynamicPropertyList;

    @ApiModelProperty(value = "登录用户关联的租户")
    private Map<String,Long> userTenantNameAndIdMap;

    public List<Long> getTenantIdList() {
        return tenantIdList;
    }

    public void setTenantIdList(List<Long> tenantIdList) {
        this.tenantIdList = tenantIdList;
    }

    public List<RefreshTokenResourceView> getRefreshTokenResourceList() {
        return refreshTokenResourceList;
    }

    public void setRefreshTokenResourceList(List<RefreshTokenResourceView> refreshTokenResourceList) {
        this.refreshTokenResourceList = refreshTokenResourceList;
    }

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

    public Map<String, Long> getUserTenantNameAndIdMap() {
        return userTenantNameAndIdMap;
    }

    public void setUserTenantNameAndIdMap(Map<String, Long> userTenantNameAndIdMap) {
        this.userTenantNameAndIdMap = userTenantNameAndIdMap;
    }
}
