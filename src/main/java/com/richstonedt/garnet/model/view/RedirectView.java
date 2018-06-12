package com.richstonedt.garnet.model.view;

import com.richstonedt.garnet.model.User;
import io.swagger.annotations.ApiModelProperty;

public class RedirectView {

    @ApiModelProperty(value = "登录用户")
    private User user;

    @ApiModelProperty(value = "登录状态")
    private String loginStatus;

    @ApiModelProperty(value = "重定向链接")
    private String redirectUrl;

    @ApiModelProperty(value = "返回信息")
    private String message;

    @ApiModelProperty(value = "accessToken")
    private String accessToken;

    @ApiModelProperty(value = "refreshToken")
    private String refreshToken;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
