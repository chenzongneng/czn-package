/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.job.service.impl;

import com.richstonedt.garnet.modules.job.dao.ScheduleJobLogDao;
import com.richstonedt.garnet.modules.job.entity.ScheduleJobLogEntity;
import com.richstonedt.garnet.modules.job.service.ScheduleJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * The type Schedule job log service.
 *
 * @since garnet-core-be-fe 1.0.0
 */
@Service("scheduleJobLogService")
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {

    /**
     * The Schedule job log dao.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private ScheduleJobLogDao scheduleJobLogDao;

    /**
     * 根据ID，查询定时任务日志
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public ScheduleJobLogEntity queryObject(Long jobId) {
        return scheduleJobLogDao.queryObject(jobId);
    }

    /**
     * 查询定时任务日志列表
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public List<ScheduleJobLogEntity> queryList(Map<String, Object> map) {
        return scheduleJobLogDao.queryList(map);
    }

    /**
     * 查询总数
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public int queryTotal(Map<String, Object> map) {
        return scheduleJobLogDao.queryTotal(map);
    }

    /**
     * 保存定时任务日志
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public void save(ScheduleJobLogEntity log) {
        scheduleJobLogDao.save(log);
    }

}
