/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarSysMenuDao;
import com.richstonedt.garnet.model.GarSysMenu;
import com.richstonedt.garnet.service.GarSysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>GarSysMenuServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/19 17:04
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class GarSysMenuServiceImpl implements GarSysMenuService {

    @Autowired
    private GarSysMenuDao sysMenuDao;

    /**
     * Gets user menu list.
     *
     * @return the user menu list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarSysMenu> getUserMenuList() {
        //查询根菜单列表
        List<GarSysMenu> menuList = sysMenuDao.queryListParentId(0L);
        //递归获取子菜单
        getMenuTreeList(menuList);
        return menuList;
    }

    private List<GarSysMenu> getMenuTreeList(List<GarSysMenu> menuList) {
        List<GarSysMenu> subMenuList = new ArrayList<>();
        for (GarSysMenu entity : menuList) {
            if (entity.getType() == 0) {//目录
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId())));
            }
            subMenuList.add(entity);
        }
        return subMenuList;
    }

    private List<GarSysMenu> queryListParentId(Long parentId) {
        return sysMenuDao.queryListParentId(parentId);
    }
}
