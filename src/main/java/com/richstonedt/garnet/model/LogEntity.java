package com.richstonedt.garnet.model;

import io.swagger.models.auth.In;

public class LogEntity {

    String module;

    String method;

    String userName;

    Long createdTime;

    Long updaedTime;

    Integer result;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getUpdaedTime() {
        return updaedTime;
    }

    public void setUpdaedTime(Long updaedTime) {
        this.updaedTime = updaedTime;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
