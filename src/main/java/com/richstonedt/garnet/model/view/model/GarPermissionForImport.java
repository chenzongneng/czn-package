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

    private GarPermission permission;

    private List<GarPermission> permissionList = new ArrayList<>();

    public GarPermission getPermission() {
        return permission;
    }

    public void setPermission(GarPermission permission) {
        this.permission = permission;
    }

    public List<GarPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<GarPermission> permissionList) {
        this.permissionList = permissionList;
    }
}
