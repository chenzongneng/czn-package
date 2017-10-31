/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarLogDao;
import com.richstonedt.garnet.model.GarLog;
import com.richstonedt.garnet.service.GarLogService;
import com.richstonedt.garnet.utils.IdGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b><code>GarLogServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/31 18:03
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class GarLogServiceImpl implements GarLogService {

    /**
     * The Log dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarLogDao logDao;

    /**
     * Save.
     *
     * @param garLog the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarLog garLog) {
        if (garLog.getId() == null) {
            garLog.setId(IdGeneratorUtil.generateId());
        }
        logDao.save(garLog);
    }

    /**
     * Update.
     *
     * @param garLog the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarLog garLog) {
        logDao.update(garLog);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void deleteById(Long id) {
        logDao.deleteById(id);
    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Long> ids) {
        logDao.deleteBatch(ids);
    }

    /**
     * Query object t.
     *
     * @param id the id
     * @return the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarLog queryObject(Long id) {
        return logDao.queryObject(id);
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
    public List<GarLog> queryObjects(String searchName, Integer page, Integer limit) {
        Integer offset = (page - 1) * limit;
        return logDao.queryObjects(searchName, limit, offset);
    }

    /**
     * Query total int.
     *
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal() {
        return logDao.queryTotal();
    }
}
