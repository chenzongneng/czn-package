/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarLogOperationDao;
import com.richstonedt.garnet.model.GarLogOperation;
import com.richstonedt.garnet.service.GarLogOperationService;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b><code>GarLogOperationServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/31 18:08
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class GarLogOperationServiceImpl implements GarLogOperationService {

    /**
     * The Log operation dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarLogOperationDao logOperationDao;

    /**
     * Save.
     *
     * @param garLogOperation the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarLogOperation garLogOperation) {
        if (garLogOperation.getId() == null) {
            garLogOperation.setId(IdGeneratorUtil.generateId());
        }
        logOperationDao.save(garLogOperation);
    }

    /**
     * Update.
     *
     * @param garLogOperation the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarLogOperation garLogOperation) {
        logOperationDao.update(garLogOperation);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void deleteById(Long id) {
        logOperationDao.deleteById(id);
    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Long> ids) {
        logOperationDao.deleteBatch(ids);
    }

    /**
     * Query object t.
     *
     * @param id the id
     * @return the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarLogOperation queryObject(Long id) {
        return logOperationDao.queryObject(id);
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
    public List<GarLogOperation> queryObjects(String searchName, Integer page, Integer limit) {
        return null;
    }

    /**
     * Query total int.
     *
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal() {
        return 0;
    }

    /**
     * Gets all operations.
     *
     * @return the all operations
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarLogOperation> getAllOperations() {
        return logOperationDao.getAllOperations();
    }
}
