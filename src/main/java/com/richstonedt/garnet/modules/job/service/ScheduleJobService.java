/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.job.service;

import com.richstonedt.garnet.modules.job.entity.ScheduleJobEntity;

import java.util.List;
import java.util.Map;

/**
 * 定时任务
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月28日 上午9:55:32
 * @since garnet-core-be-fe 1.0.0
 */
public interface ScheduleJobService {

    /**
     * 根据ID，查询定时任务
     *
     * @since garnet-core-be-fe 1.0.0
     */
    ScheduleJobEntity queryObject(Long jobId);

    /**
     * 查询定时任务列表
     *
     * @since garnet-core-be-fe 1.0.0
     */
    List<ScheduleJobEntity> queryList(Map<String, Object> map);

    /**
     * 查询总数
     *
     * @since garnet-core-be-fe 1.0.0
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 保存定时任务
     *
     * @since garnet-core-be-fe 1.0.0
     */
    void save(ScheduleJobEntity scheduleJob);

    /**
     * 更新定时任务
     *
     * @since garnet-core-be-fe 1.0.0
     */
    void update(ScheduleJobEntity scheduleJob);

    /**
     * 批量删除定时任务
     *
     * @since garnet-core-be-fe 1.0.0
     */
    void deleteBatch(Long[] jobIds);

    /**
     * 批量更新定时任务状态
     *
     * @since garnet-core-be-fe 1.0.0
     */
    int updateBatch(Long[] jobIds, int status);

    /**
     * 立即执行
     *
     * @since garnet-core-be-fe 1.0.0
     */
    void run(Long[] jobIds);

    /**
     * 暂停运行
     *
     * @since garnet-core-be-fe 1.0.0
     */
    void pause(Long[] jobIds);

    /**
     * 恢复运行
     *
     * @since garnet-core-be-fe 1.0.0
     */
    void resume(Long[] jobIds);
}
