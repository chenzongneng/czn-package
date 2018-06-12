package com.richstonedt.garnet.model.view;

import io.swagger.annotations.ApiModelProperty;

public class GarLoginView {

    private String userName;

    private String password;

    @ApiModelProperty(value = "登录应用的应用标识")
    private String appCode;

    @ApiModelProperty(value = "获取验证码的时间")
    private String nowTime;

    @ApiModelProperty(value = "验证码")
    private String kaptcha;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getKaptcha() {
        return kaptcha;
    }

    public void setKaptcha(String kaptcha) {
        this.kaptcha = kaptcha;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNowTime() {
        return nowTime;
    }

    public void setNowTime(String nowTime) {
        this.nowTime = nowTime;
    }
}
