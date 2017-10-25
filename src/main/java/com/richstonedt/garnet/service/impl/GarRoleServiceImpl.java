/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarRoleDao;
import com.richstonedt.garnet.model.GarRole;
import com.richstonedt.garnet.service.GarRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b><code>GarRoleServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/25 14:07
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class GarRoleServiceImpl implements GarRoleService {

    /**
     * The Role dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarRoleDao roleDao;

    /**
     * Save.
     *
     * @param garRole the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarRole garRole) {
        roleDao.save(garRole);
    }

    /**
     * Update.
     *
     * @param garRole the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarRole garRole) {
        update(garRole);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void deleteById(Long id) {
        roleDao.deleteById(id);
    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Long> ids) {
        roleDao.deleteBatch(ids);
    }

    /**
     * Query object t.
     *
     * @param id the id
     * @return the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarRole queryObject(Long id) {
        return roleDao.queryObject(id);
    }

    /**
     * Query objects list.
     *
     * @param searchName the search name
     * @param page       the offset
     * @param limit      the limit
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarRole> queryObjects(String searchName, Integer page, Integer limit) {
        Integer offset = (page - 1) * limit;
        return roleDao.queryObjects(searchName, limit, offset);
    }

    /**
     * Query total int.
     *
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal() {
        return roleDao.queryTotal();
    }
}
