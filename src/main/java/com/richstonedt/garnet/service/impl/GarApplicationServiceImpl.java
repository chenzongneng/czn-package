/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarApplicationDao;
import com.richstonedt.garnet.model.GarApplication;
import com.richstonedt.garnet.service.GarApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b><code>GarApplicationServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/17 10:29
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class GarApplicationServiceImpl implements GarApplicationService {

    /**
     * The Application dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarApplicationDao applicationDao;

    /**
     * Save.
     *
     * @param garApplication the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarApplication garApplication) {
        applicationDao.save(garApplication);
    }

    /**
     * Update.
     *
     * @param garApplication the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarApplication garApplication) {
        applicationDao.update(garApplication);
    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Integer> ids) {
        applicationDao.deleteBatch(ids);
    }

    /**
     * Query object t.
     *
     * @param id the id
     * @return the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarApplication queryObject(Integer id) {
        return applicationDao.queryObject(id);
    }

    /**
     * Query total int.
     *
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal() {
        return applicationDao.queryTotal();
    }

    /**
     * Query objects list.
     *
     * @param searchName the search name
     * @param limit      the limit
     * @param page       the offset
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarApplication> queryObjects(String searchName, Integer page, Integer limit) {
        Integer offset = (page - 1) * limit;
        return applicationDao.queryObjects(searchName, limit, offset);
    }
}
