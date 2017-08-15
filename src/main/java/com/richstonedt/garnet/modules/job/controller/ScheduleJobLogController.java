/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.job.controller;

import com.richstonedt.garnet.common.utils.PageUtils;
import com.richstonedt.garnet.common.utils.Query;
import com.richstonedt.garnet.common.utils.Result;
import com.richstonedt.garnet.modules.job.entity.ScheduleJobLogEntity;
import com.richstonedt.garnet.modules.job.service.ScheduleJobLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 定时任务日志
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月1日 下午10:39:52
 * @since garnet-core-be-fe 1.0.0
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {

    /**
     * The Schedule job log service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private ScheduleJobLogService scheduleJobLogService;

    /**
     * 定时任务日志列表
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:log")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ScheduleJobLogEntity> jobList = scheduleJobLogService.queryList(query);
        int total = scheduleJobLogService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(jobList, total, query.getLimit(), query.getPage());

        return Result.ok().put("page", pageUtil);
    }

    /**
     * 定时任务日志信息
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping("/info/{logId}")
    public Result info(@PathVariable("logId") Long logId) {
        ScheduleJobLogEntity log = scheduleJobLogService.queryObject(logId);

        return Result.ok().put("log", log);
    }
}
