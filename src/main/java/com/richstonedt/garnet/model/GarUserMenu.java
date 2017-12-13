package com.richstonedt.garnet.model;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>GarUserMenu</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>10:43
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
public class GarUserMenu extends GarMenu implements Serializable {

    private Long userId;

    private List<GarUserMenu> menuList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<GarUserMenu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<GarUserMenu> menuList) {
        this.menuList = menuList;
    }
}
