/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarRoleAuthorityDao;
import com.richstonedt.garnet.model.GarRoleAuthority;
import com.richstonedt.garnet.service.GarRoleAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * <b><code>GarRoleAuthorityServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 11:12
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Service
public class GarRoleAuthorityServiceImpl implements GarRoleAuthorityService {

    @Autowired
    private GarRoleAuthorityDao roleAuthorityDao;
    
    @Override
    public List<GarRoleAuthority> getRoleAuthorityByRoleId(Long roleId) {
        return roleAuthorityDao.getRoleAuthorityByRoleId(roleId);
    }

    @Override
    public List<GarRoleAuthority> getRoleAuthorityByAuthorityId(Long authorityId) {
        return roleAuthorityDao.getRoleAuthorityByAuthorityId(authorityId);
    }

    @Override
    public void deleteRoleAuthorityByAuthorityId(Long authorityId) {
        roleAuthorityDao.deleteRoleAuthorityByAuthorityId(authorityId);
    }

    @Override
    public Set<Long> getAuthorityIdsByRoleIds(Set<Long> roleIds) {
        return roleAuthorityDao.getAuthorityIdsByRoleIds(roleIds);
    }

    @Override
    public void save(GarRoleAuthority garRoleAuthority) {
        roleAuthorityDao.save(garRoleAuthority);
    }

    @Override
    public void update(GarRoleAuthority garRoleAuthority) {
        roleAuthorityDao.update(garRoleAuthority);
    }

    @Override
    public void deleteById(Long id) {
        roleAuthorityDao.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        roleAuthorityDao.deleteBatch(ids);
    }

    @Override
    public GarRoleAuthority queryObject(Long id) {
        return roleAuthorityDao.queryObject(id);
    }

    @Override
    public List<GarRoleAuthority> queryObjects(String searchName, Integer page, Integer limit) {
        return roleAuthorityDao.queryObjects(searchName,page,limit);
    }

    @Override
    public int queryTotal() {
        return roleAuthorityDao.queryTotal();
    }
}
