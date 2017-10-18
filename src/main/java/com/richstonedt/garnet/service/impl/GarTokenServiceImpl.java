/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarTokenDao;
import com.richstonedt.garnet.model.GarToken;
import com.richstonedt.garnet.service.GarTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b><code>GarTokenServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/18 14:39
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class GarTokenServiceImpl implements GarTokenService {

    /**
     * The Token dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarTokenDao tokenDao;

    /**
     * Save.
     *
     * @param garToken the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void save(GarToken garToken) {
        tokenDao.save(garToken);
    }

    /**
     * Update.
     *
     * @param garToken the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void update(GarToken garToken) {
        tokenDao.update(garToken);
    }

    /**
     * Delete by id.
     *
     * @param id the id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void deleteById(Integer id) {

    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Integer> ids) {

    }

    /**
     * Query object t.
     *
     * @param id the id
     * @return the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarToken queryObject(Integer id) {
        return tokenDao.queryObject(id);
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
    public List<GarToken> queryObjects(String searchName, Integer page, Integer limit) {
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
     * Query by token gar token.
     *
     * @param token the token
     * @return the gar token
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarToken queryByToken(String token) {
        return tokenDao.queryByToken(token);
    }
}
