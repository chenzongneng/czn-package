/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarAuthorityPermissionDao;
import com.richstonedt.garnet.model.GarAuthorityPermission;
import com.richstonedt.garnet.service.GarAuthorityPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <b><code>GarAuthorityPermissionServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 15:12
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Service
public class GarAuthorityPermissionServiceImpl implements GarAuthorityPermissionService {

    @Autowired
    private GarAuthorityPermissionDao authorityPermissionDao;

    @Override
    public List<GarAuthorityPermission> getAuthorityPermissionByAuthorityId(Long authorityId) {
        return authorityPermissionDao.getAuthorityPermissionByAuthorityId(authorityId);
    }

    @Override
    public List<GarAuthorityPermission> getAuthorityPermissionByPermissionId(Long permissionId) {
        return authorityPermissionDao.getAuthorityPermissionByPermissionId(permissionId);
    }

    @Override
    public void deleteAuthorityPermissionByAuthorityId(Long permissionId) {
        authorityPermissionDao.deleteAuthorityPermissionByAuthorityId(permissionId);
    }

    @Override
    public Set<Long> getPermissionIdsByAuthorityIds(Set<Long> ids) {
        return authorityPermissionDao.getPermissionIdsByAuthorityIds(ids);
    }

    @Override
    public void save(GarAuthorityPermission garAuthorityPermission) {
        authorityPermissionDao.save(garAuthorityPermission);
    }

    @Override
    public void update(GarAuthorityPermission garAuthorityPermission) {
        authorityPermissionDao.update(garAuthorityPermission);
    }

    @Override
    public void deleteById(Long id) {
        authorityPermissionDao.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        authorityPermissionDao.deleteBatch(ids);
    }

    @Override
    public GarAuthorityPermission queryObject(Long id) {
        return authorityPermissionDao.queryObject(id);
    }

    @Override
    public List<GarAuthorityPermission> queryObjects(String searchName, Integer page, Integer limit) {
        return authorityPermissionDao.queryObjects(searchName,page,limit);
    }

    @Override
    public int queryTotal() {
        return authorityPermissionDao.queryTotal();
    }
}
