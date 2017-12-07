/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarAuthorityDao;
import com.richstonedt.garnet.model.GarAuthority;
import com.richstonedt.garnet.model.view.model.GarVMAuthority;
import com.richstonedt.garnet.service.GarAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>GarAuthorityServiceImpl</code></b>
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
public class GarAuthorityServiceImpl implements GarAuthorityService {

    @Autowired
    private GarAuthorityDao authorityDao;


    private BeanCopier entityToVMCopier = BeanCopier.create(GarAuthority.class, GarVMAuthority.class,
            false);

    private BeanCopier vmToEntityCopier = BeanCopier.create(GarVMAuthority.class, GarAuthority.class,
            false);

    @Override
    public void save(GarAuthority garAuthority) {
        authorityDao.save(garAuthority);
    }

    @Override
    public void update(GarAuthority garAuthority) {
        authorityDao.update(garAuthority);
    }

    @Override
    public void deleteById(Long id) {
        authorityDao.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        authorityDao.deleteBatch(ids);
    }

    @Override
    public GarAuthority queryObject(Long id) {
        return authorityDao.queryObject(id);
    }

    @Override
    public List<GarAuthority> queryObjects(String searchName, Integer page, Integer limit) {
        return authorityDao.queryObjects(searchName, page, limit);
    }

    @Override
    public int queryTotal() {
        return authorityDao.queryTotal();
    }

    @Override
    public List<GarVMAuthority> queryAuthorityList(String searchName, Integer page, Integer limit) {
        Integer offset = (page - 1) * limit;
        List<GarAuthority> authorities = authorityDao.queryObjects(searchName,limit,offset);
        List<GarVMAuthority> result = new ArrayList<>();
        for (GarAuthority authority : authorities) {
            result.add(convertAuthorityToVmAuthority(authority));
        }
        return result;
    }

    @Override
    public void saveAuthority(GarVMAuthority garVMAuthority) {
        GarAuthority authority = this.convertVmAuthorityToAuthority(garVMAuthority);
        authorityDao.save(authority);
    }

    @Override
    public GarVMAuthority searchAuthority(Long authorityId) {
        GarAuthority authority = authorityDao.queryObject(authorityId);
        return convertAuthorityToVmAuthority(authority);
    }

    @Override
    public void updateAuthority(GarVMAuthority garVMAuthority) {
        GarAuthority authority = this.convertVmAuthorityToAuthority(garVMAuthority);
        update(authority);
    }

    private GarVMAuthority convertAuthorityToVmAuthority(GarAuthority garAuthority) {
        GarVMAuthority vmAuthority = new GarVMAuthority();
        entityToVMCopier.copy(garAuthority,vmAuthority,null);
        return vmAuthority;
    }

    private GarAuthority convertVmAuthorityToAuthority(GarVMAuthority vmAuthority) {
        GarAuthority authority = new GarVMAuthority();
        vmToEntityCopier.copy(vmAuthority,authority,null);
        return authority;
    }
}
