package com.richstonedt.garnet.model.view;

import com.richstonedt.garnet.model.UserCredential;
import io.swagger.annotations.ApiModelProperty;

public class UserCredentialView {

    @ApiModelProperty(value = "所属用户ID")
    private Long userId;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "旧密码")
    private String newPassword;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
