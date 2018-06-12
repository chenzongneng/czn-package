package com.richstonedt.garnet.model.view;

import com.richstonedt.garnet.model.Permission;
import com.richstonedt.garnet.model.RolePermission;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * The type Permission view.
 */
public class PermissionView {

    private Permission permission;

    private List<RolePermission> rolePermissions;

    @ApiModelProperty(value = "关联的应用名称")
    private String applicationName;

    @ApiModelProperty(value = "关联的租户名称")
    private String tenantName;

    @ApiModelProperty(value = "类型（应用、租户、租户+应用）")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    /**
     * Gets permission.
     *
     * @return the permission
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * Sets permission.
     *
     * @param permission the permission
     */
    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    /**
     * Gets role permissions.
     *
     * @return the role permissions
     */
    public List<RolePermission> getRolePermissions() {
        return rolePermissions;
    }

    /**
     * Sets role permissions.
     *
     * @param rolePermissions the role permissions
     */
    public void setRolePermissions(List<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }
}
