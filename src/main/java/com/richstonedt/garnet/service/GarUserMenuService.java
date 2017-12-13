package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarUserMenu;

import java.util.List;
import java.util.Map;

/**
 * <b><code>GarUserMenuService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>10:59
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
public interface GarUserMenuService {

    List<GarUserMenu> getUserMenuList(Long userId, Long appId);

    Map<String, Boolean> getButtonCodeMapListByUserId(Long userId, Long appId);

}
