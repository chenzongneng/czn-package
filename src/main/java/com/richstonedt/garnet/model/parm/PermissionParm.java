package com.richstonedt.garnet.model.parm;

import com.richstonedt.garnet.model.Permission;

/**
 * The type Permission parm.
 */
public class PermissionParm extends BaseParm{

    private Permission permission;


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
}
