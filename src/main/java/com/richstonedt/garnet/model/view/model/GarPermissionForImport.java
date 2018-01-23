package com.richstonedt.garnet.model.view.model;

import com.richstonedt.garnet.model.GarPermission;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>GarPermissionForImport</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>16:15
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@ApiModel(value = "访问权限导入数据")
public class GarPermissionForImport {

    /**
     * The Permission.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private GarPermission permission;

    /**
     * The Permission list.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    private List<GarPermission> permissionList = new ArrayList<>();

    /**
     * Gets permission.
     *
     * @return the permission
     * @since garnet-core-be-fe 0.1.0
     */
    public GarPermission getPermission() {
        return permission;
    }

    /**
     * Sets permission.
     *
     * @param permission the permission
     * @since garnet-core-be-fe 0.1.0
     */
    public void setPermission(GarPermission permission) {
        this.permission = permission;
    }

    /**
     * Gets permission list.
     *
     * @return the permission list
     * @since garnet-core-be-fe 0.1.0
     */
    public List<GarPermission> getPermissionList() {
        return permissionList;
    }

    /**
     * Sets permission list.
     *
     * @param permissionList the permission list
     * @since garnet-core-be-fe 0.1.0
     */
    public void setPermissionList(List<GarPermission> permissionList) {
        this.permissionList = permissionList;
    }
}
