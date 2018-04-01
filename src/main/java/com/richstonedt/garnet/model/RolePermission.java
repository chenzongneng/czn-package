package com.richstonedt.garnet.model;

import java.io.Serializable;

/**
 *
 * TABLE:gar_role_permissions
 *
 * @mbg.generated 该代码为自动生成，请不要修改
 *
 * DATE: 2018-03-30 13:23
 */
public class RolePermission implements Serializable {
    private Long id;

    private Long roleId;

    private Long permissionId;

    /**
     * TABLE： gar_role_permissions
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * <br>
     *
     * TABLE： gar_role_permissions<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        RolePermission other = (RolePermission) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getPermissionId() == null ? other.getPermissionId() == null : this.getPermissionId().equals(other.getPermissionId()));
    }

    /**
     * <br>
     *
     * TABLE： gar_role_permissions<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getPermissionId() == null) ? 0 : getPermissionId().hashCode());
        return result;
    }

    /**
     * <br>
     *
     * TABLE： gar_role_permissions<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", roleId=").append(roleId);
        sb.append(", permissionId=").append(permissionId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}