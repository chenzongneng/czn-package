/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.utils.GarnetRsUtil;
import com.richstonedt.garnet.dao.GarApplicationDao;
import com.richstonedt.garnet.dao.GarMenuDao;
import com.richstonedt.garnet.dao.GarMenuPermissionDao;
import com.richstonedt.garnet.model.GarMenu;
import com.richstonedt.garnet.model.view.model.GarVMMenu;
import com.richstonedt.garnet.service.GarMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>GarMenuServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 11:44
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Service
public class GarMenuServiceImpl implements GarMenuService {

    @Autowired
    private GarMenuDao menuDao;

    @Autowired
    private GarMenuPermissionDao menuPermissionDao;

    @Autowired
    private GarApplicationDao applicationDao;


    private BeanCopier entityToVMCopier = BeanCopier.create(GarMenu.class, GarVMMenu.class,
            false);

    private BeanCopier vmToEntityCopier = BeanCopier.create(GarVMMenu.class, GarMenu.class,
            false);

    @Override
    public void save(GarMenu garMenu) {
        menuDao.save(garMenu);
    }

    @Override
    public void update(GarMenu garMenu) {
        menuDao.update(garMenu);
    }

    @Override
    public void deleteById(Long id) {
        menuDao.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        menuDao.deleteBatch(ids);
    }

    @Override
    public GarMenu queryObject(Long id) {
        return menuDao.queryObject(id);
    }

    @Override
    public List<GarMenu> queryObjects(String searchName, Integer page, Integer limit) {
        return menuDao.queryObjects(searchName, page, limit);
    }

    @Override
    public int queryTotal() {
        return menuDao.queryTotal();
    }

    @Override
    public List<GarVMMenu> queryMenuList(String searchName, Long applicationId, Integer page, Integer limit) {
        Integer offset = (page - 1) * limit;
        List<GarMenu> authorities = menuDao.queryMenus(searchName,applicationId, limit, offset);
        List<GarVMMenu> result = new ArrayList<>();
        for (GarMenu menu : authorities) {
            result.add(convertMenuToVmMenu(menu));
        }
        return result;
    }

    @Override
    public List<GarVMMenu> queryMenuListByAppId(Long appId) {
        List<GarMenu> menuList = menuDao.getMenusByAppId(appId);
        List<GarVMMenu> vmMenuList = new ArrayList<>();
        for (GarMenu menu : menuList) {
            GarVMMenu vmMenu = convertMenuToVmMenu(menu);
            vmMenuList.add(vmMenu);
        }
        return vmMenuList;
    }

    @Override
    public void saveMenu(GarVMMenu garVMMenu) {
        menuDao.save(garVMMenu);
        saveMenuPermission(garVMMenu);
    }

    @Override
    public GarVMMenu searchMenu(Long menuId) {
        GarMenu menu = menuDao.queryObject(menuId);
        return convertMenuToVmMenu(menu);
    }

    @Override
    public void updateMenu(GarVMMenu vmMenu) {
        update(vmMenu);
        menuPermissionDao.deleteByMenuId(vmMenu.getMenuId());
        saveMenuPermission(vmMenu);
    }

    private GarVMMenu convertMenuToVmMenu(GarMenu garMenu) {
        GarVMMenu vmMenu = new GarVMMenu();
        entityToVMCopier.copy(garMenu, vmMenu, null);
        vmMenu.setParentName(menuDao.getMenuNameByCode(garMenu.getParentCode()));
        vmMenu.setApplicationName(applicationDao.queryObject(garMenu.getApplicationId()).getName());
        vmMenu.setPermissionIdList(menuPermissionDao.getPermissionIdByMenuId(vmMenu.getMenuId()));
        return vmMenu;
    }

    private void saveMenuPermission(GarVMMenu vmMenu) {
        List<Long> permissionIdList = GarnetRsUtil.parseStringToList(vmMenu.getPermissionIds());
        for (Long permissionId : permissionIdList) {
            menuPermissionDao.save(vmMenu.getMenuId(), permissionId);
        }
    }

}
