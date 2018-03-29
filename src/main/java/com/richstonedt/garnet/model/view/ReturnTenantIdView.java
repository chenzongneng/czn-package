package com.richstonedt.garnet.model.view;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ReturnTenantIdView {

    @ApiModelProperty(value = "登录用户关联的租户id")
    private List<Long> tenantIds;

    @ApiModelProperty(value = "判断登录用户是不是超级管理员标志")
    private boolean superAdmin;

    public List<Long> getTenantIds() {
        return tenantIds;
    }

    public void setTenantIds(List<Long> tenantIds) {
        this.tenantIds = tenantIds;
    }

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
    }
}
