package com.richstonedt.garnet.model.view;

import com.richstonedt.garnet.model.UserCredential;

public class UserCredentialView {

    private Long userId;

    private String password;

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
