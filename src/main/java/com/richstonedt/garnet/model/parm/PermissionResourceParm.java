package com.richstonedt.garnet.model.parm;

import java.util.List;

/**
 * The type Permission resource parm.
 */
public class PermissionResourceParm extends BaseParm{

    private List<Long> permissionIds;

    /**
     * Gets permission ids.
     *
     * @return the permission ids
     */
    public List<Long> getPermissionIds() {
        return permissionIds;
    }

    /**
     * Sets permission ids.
     *
     * @param permissionIds the permission ids
     */
    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }
}
