package com.richstonedt.garnet.model.view;

/**
 * The type Login view.
 */
public class LoginView {

    private String userName;

    private String password;

    private String vcode;

    private String appCode;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets vcode.
     *
     * @return the vcode
     */
    public String getVcode() {
        return vcode;
    }

    /**
     * Sets vcode.
     *
     * @param vcode the vcode
     */
    public void setVcode(String vcode) {
        this.vcode = vcode;
    }
}
