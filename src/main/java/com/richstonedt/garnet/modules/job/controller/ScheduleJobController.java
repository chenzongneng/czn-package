/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.job.controller;

import com.richstonedt.garnet.common.annotation.SysLog;
import com.richstonedt.garnet.common.utils.PageUtils;
import com.richstonedt.garnet.common.utils.Query;
import com.richstonedt.garnet.common.utils.Result;
import com.richstonedt.garnet.common.validator.ValidatorUtils;
import com.richstonedt.garnet.modules.job.entity.ScheduleJobEntity;
import com.richstonedt.garnet.modules.job.service.ScheduleJobService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 定时任务
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月28日 下午2:16:40
 * @since garnet-core-be-fe 1.0.0
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {

    /**
     * The Schedule job service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 定时任务列表
     *
     * @param params the  Map<String, Object>
     * @return the Result
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ScheduleJobEntity> jobList = scheduleJobService.queryList(query);
        int total = scheduleJobService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(jobList, total, query.getLimit(), query.getPage());

        return Result.ok().put("page", pageUtil);
    }

    /**
     * 定时任务信息
     *
     * @param jobId the Long
     * @return the Result
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping("/info/{jobId}")
    @RequiresPermissions("sys:schedule:info")
    public Result info(@PathVariable("jobId") Long jobId) {
        ScheduleJobEntity schedule = scheduleJobService.queryObject(jobId);

        return Result.ok().put("schedule", schedule);
    }

    /**
     * 保存定时任务
     *
     * @return the Result
     * @since garnet-core-be-fe 1.0.0
     */
    @SysLog("保存定时任务")
    @RequestMapping("/save")
    @RequiresPermissions("sys:schedule:save")
    public Result save(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);

        scheduleJobService.save(scheduleJob);

        return Result.ok();
    }

    /**
     * 修改定时任务
     *
     * @return the Result
     * @since garnet-core-be-fe 1.0.0
     */
    @SysLog("修改定时任务")
    @RequestMapping("/update")
    @RequiresPermissions("sys:schedule:update")
    public Result update(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);

        scheduleJobService.update(scheduleJob);

        return Result.ok();
    }

    /**
     * 删除定时任务
     *
     * @return the Result
     * @since garnet-core-be-fe 1.0.0
     */
    @SysLog("删除定时任务")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:schedule:delete")
    public Result delete(@RequestBody Long[] jobIds) {
        scheduleJobService.deleteBatch(jobIds);

        return Result.ok();
    }

    /**
     * 立即执行任务
     *
     * @return the Result
     * @since garnet-core-be-fe 1.0.0
     */
    @SysLog("立即执行任务")
    @RequestMapping("/run")
    @RequiresPermissions("sys:schedule:run")
    public Result run(@RequestBody Long[] jobIds) {
        scheduleJobService.run(jobIds);

        return Result.ok();
    }

    /**
     * 暂停定时任务
     *
     * @return the Result
     * @since garnet-core-be-fe 1.0.0
     */
    @SysLog("暂停定时任务")
    @RequestMapping("/pause")
    @RequiresPermissions("sys:schedule:pause")
    public Result pause(@RequestBody Long[] jobIds) {
        scheduleJobService.pause(jobIds);

        return Result.ok();
    }

    /**
     * 恢复定时任务
     *
     * @return the Result
     * @since garnet-core-be-fe 1.0.0
     */
    @SysLog("恢复定时任务")
    @RequestMapping("/resume")
    @RequiresPermissions("sys:schedule:resume")
    public Result resume(@RequestBody Long[] jobIds) {
        scheduleJobService.resume(jobIds);

        return Result.ok();
    }

}
