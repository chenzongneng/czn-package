/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.dao.GarTokenDao;
import com.richstonedt.garnet.model.GarToken;
import com.richstonedt.garnet.model.GarUser;
import com.richstonedt.garnet.service.GarTokenService;
import com.richstonedt.garnet.service.GarUserDeptService;
import com.richstonedt.garnet.service.GarUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
     * The User service.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private GarUserService userService;

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
    public void deleteById(Long id) {

    }

    /**
     * Delete batch.
     *
     * @param ids the ids
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public void deleteBatch(List<Long> ids) {

    }

    /**
     * Query object t.
     *
     * @param id the id
     * @return the t
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarToken queryObject(Long id) {
        return tokenDao.queryObject(id);
    }

    /**
     * Query objects list.
     *
     * @param params the params
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<GarToken> queryObjects(Map<String,Object> params) {
        return tokenDao.queryObjects(params);
    }

    /**
     * Query total int.
     *
     * @param params the params
     * @return the int
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public int queryTotal(Map<String,Object> params) {
        return tokenDao.queryTotal(params);
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

    /**
     * Gets user info by token.
     *
     * @param token the token
     * @return the user info by token
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public GarUser getUserInfoByToken(String token) {
        GarToken garToken = queryByToken(token);
        return userService.queryObject(garToken.getUserId());
    }
}
