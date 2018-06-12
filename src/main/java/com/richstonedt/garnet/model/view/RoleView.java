package com.richstonedt.garnet.model.view;

import com.richstonedt.garnet.model.GroupRole;
import com.richstonedt.garnet.model.Role;
import com.richstonedt.garnet.model.RolePermission;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * The type Role view.
 */
public class RoleView {

    private Role role;

    private List<GroupRole> groupRoles;

    private List<RolePermission> rolePermissions;

    @ApiModelProperty(value = "选择关联的组ID")
    private List<Long> groupIds;

    @ApiModelProperty(value = "选择关联的权限ID")
    private List<Long> permissionIds;

    @ApiModelProperty(value = "关联的组的名称")
    private List<String> groupNames;

    @ApiModelProperty(value = "关联的权限的名称")
    private List<String> permissionNames;

    @ApiModelProperty(value = "关联的租户名称")
    private String tenantName;

    @ApiModelProperty(value = "关联的应用名称")
    private String applicationName;

    @ApiModelProperty(value = "类型（租户、应用、租户+应用）")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public List<String> getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(List<String> groupNames) {
        this.groupNames = groupNames;
    }

    public List<String> getPermissionNames() {
        return permissionNames;
    }

    public void setPermissionNames(List<String> permissionNames) {
        this.permissionNames = permissionNames;
    }

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
