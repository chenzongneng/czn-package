package com.richstonedt.garnet.model.view;

import com.richstonedt.garnet.model.GroupRole;
import com.richstonedt.garnet.model.Role;
import com.richstonedt.garnet.model.RolePermission;

import java.util.List;

/**
 * The type Role view.
 */
public class RoleView {

    private Role role;

    private List<GroupRole> groupRoles;

    private List<RolePermission> rolePermissions;

    private List<Long> groupIds;

    private List<Long> permissionIds;

    public List<Long> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Long> groupIds) {
        this.groupIds = groupIds;
    }

    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Gets group roles.
     *
     * @return the group roles
     */
    public List<GroupRole> getGroupRoles() {
        return groupRoles;
    }

    /**
     * Sets group roles.
     *
     * @param groupRoles the group roles
     */
    public void setGroupRoles(List<GroupRole> groupRoles) {
        this.groupRoles = groupRoles;
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
