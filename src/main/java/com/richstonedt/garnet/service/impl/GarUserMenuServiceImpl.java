package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarUserMenuDao;
import com.richstonedt.garnet.model.GarUserMenu;
import com.richstonedt.garnet.service.GarUserMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b><code>GarUserMenuServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>11:00
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Service
public class GarUserMenuServiceImpl implements GarUserMenuService {

    @Autowired
    private GarUserMenuDao userMenuDao;

    @Override
    public List<GarUserMenu> getUserMenuList(Long userId, Long appId) {
        List<GarUserMenu> parentMenuList = userMenuDao.getUserMenuByUserIdAndAppIdAndParentCode(userId, appId,"Root");
        return getMenuTreeList(userId,appId, parentMenuList);
    }

    @Override
    public Map<String, Boolean> getButtonCodeMapListByUserId(Long userId, Long appId) {
        List<Map> buttonMaps = new ArrayList<>();
        List<String> buttons = userMenuDao.getButtonCodeByUserId(userId,appId);
        Map<String, Boolean> buttonMap = new HashMap<>();
        for (String button : buttons) {
            buttonMap.put(button, true);
        }
        return buttonMap;
    }

    private List<GarUserMenu> getMenuTreeList(Long userId, Long appId, List<GarUserMenu> parentMenuList) {
        List<GarUserMenu> menuList = new ArrayList<>();
        for (GarUserMenu menu : parentMenuList) {
            List<GarUserMenu> childMenus = userMenuDao.getUserMenuByUserIdAndAppIdAndParentCode(userId, appId, menu.getCode());
            if (!CollectionUtils.isEmpty(childMenus)) {
                this.getMenuTreeList(userId,appId, childMenus);
                menu.setMenuList(childMenus);
                menuList.add(menu);
            } else {
                return null;
            }
        }
        return menuList;
    }


}
