/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarUserDao;
import com.richstonedt.garnet.model.GarUser;
import com.richstonedt.garnet.service.GarUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b><code>GarUserServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/18 11:27
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class GarUserServiceImpl implements GarUserService {

    /**
     * The User dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarUserDao userDao;

    /**
     * Save.
     *
     * @param garUser the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarUser garUser) {
        userDao.save(garUser);
    }

    /**
     * Update.
     *
     * @param garUser the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarUser garUser) {
        userDao.update(garUser);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void deleteById(Integer id) {
        userDao.deleteById(id);
    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Integer> ids) {
        userDao.deleteBatch(ids);
    }

    /**
     * Query object t.
     *
     * @param id the id
     * @return the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarUser queryObject(Integer id) {
        return userDao.queryObject(id);
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
    public List<GarUser> queryObjects(String searchName, Integer page, Integer limit) {
        Integer offset = (page - 1) * limit;
        return userDao.queryObjects(searchName, limit, offset);
    }

    /**
     * Query total int.
     *
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal() {
        return userDao.queryTotal();
    }
}
