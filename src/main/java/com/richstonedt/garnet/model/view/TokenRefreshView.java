package com.richstonedt.garnet.model.view;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "刷新token的请求mode", description = "刷新token的请求mode")
public class TokenRefreshView {

    @ApiModelProperty(value = "应用标识")
    private String appCode;

    @ApiModelProperty(value = "账号")
    private String userName;

    @ApiModelProperty(value = "refreshToken")
    private String refreshToken;

    @ApiModelProperty(value = "登录用户绑定的租户id")
    private List<Long> tenantIdList;

    public List<Long> getTenantIdList() {
        return tenantIdList;
    }

    public void setTenantIdList(List<Long> tenantIdList) {
        this.tenantIdList = tenantIdList;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
