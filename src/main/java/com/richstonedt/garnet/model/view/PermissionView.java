package com.richstonedt.garnet.model.view;

import com.richstonedt.garnet.model.Permission;
import com.richstonedt.garnet.model.RolePermission;

import java.util.List;

/**
 * The type Permission view.
 */
public class PermissionView {

    private Permission permission;

    private List<RolePermission> rolePermissions;

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