/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.log.service.impl;

import com.richstonedt.garnet.system.log.dao.LogDao;
import com.richstonedt.garnet.system.log.entity.LogEntity;
import com.richstonedt.garnet.system.log.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b><code>LogServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/9/29 14:56
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class LogServiceImpl implements LogService {

    /**
     * The Log dao.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private LogDao logDao;

    /**
     * Gets Logs
     *
     * @param page       the page
     * @param limit      the limit
     * @param searchName the search name
     * @return the user roles list
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public List<LogEntity> getLogs(Integer page, Integer limit, String searchName) {
        int offset = (page - 1) * limit;
        return logDao.getLogs(limit,offset,searchName);
    }

    /**
     * Gets Logs count.
     *
     * @return the user count
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public int getLogsCount() {
        return logDao.getLogsCount();
    }
}
