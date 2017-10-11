/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.log.dao;

import com.richstonedt.garnet.system.log.entity.LogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b><code>LogDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/9/29 14:55
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Mapper
public interface LogDao {


    /**
     * Gets logs.
     *
     * @return the distinct user id
     * @since garnet-core-be-fe 1.0.0
     */
    List<LogEntity> getLogs(@Param(value = "limit") Integer limit, @Param(value = "offset") Integer offset, @Param(value = "searchName")String searchName);

    /**
     * Gets logs count.
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
    LogEntity getLogById(@Param(value = "id") Integer id);

    /**
     * Save log.
     *
     * @param log the log
     * @since garnet-core-be-fe 1.0.0
     */
    void saveLog(LogEntity log);
}
