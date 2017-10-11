/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.log.service;

import com.richstonedt.garnet.system.log.entity.LogEntity;

import java.util.List;

/**
 * <b><code>LogService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/9/29 14:56
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
public interface LogService {

    /**
     * Gets Logs
     *
     * @param page       the page
     * @param limit      the limit
     * @param searchName the search name
     * @return the user roles list
     * @since garnet-core-be-fe 1.0.0
     */
    List<LogEntity> getLogs(Integer page, Integer limit, String searchName);

    /**
     * Gets user count.
     *
     * @return the user count
     * @since garnet-core-be-fe 1.0.0
     */
    int getLogsCount();

    /**
     * Gets log by id.
     *
     * @param id the id
     * @return the log by id
     * @since garnet-core-be-fe 1.0.0
     */
    LogEntity getLogById(Integer id);

    /**
     * Save log.
     *
     * @param log the log
     * @since garnet-core-be-fe 1.0.0
     */
    void saveLog(LogEntity log);
}
