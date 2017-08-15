/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.job.utils;

import com.google.gson.Gson;
import com.richstonedt.garnet.common.utils.SpringContextUtils;
import com.richstonedt.garnet.modules.job.entity.ScheduleJobEntity;
import com.richstonedt.garnet.modules.job.entity.ScheduleJobLogEntity;
import com.richstonedt.garnet.modules.job.service.ScheduleJobLogService;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 定时任务
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月30日 下午12:44:21
 * @since garnet-core-be-fe 1.0.0
 */
public class ScheduleJob extends QuartzJobBean {

    /**
     * The Logger.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * The Service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private ExecutorService service = Executors.newSingleThreadExecutor();

    /**
     * The execute Internal.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String jsonJob = context.getMergedJobDataMap().getString(ScheduleJobEntity.JOB_PARAM_KEY);
        ScheduleJobEntity scheduleJob = new Gson().fromJson(jsonJob, ScheduleJobEntity.class);

        //获取spring bean
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");

        //数据库保存执行记录
        ScheduleJobLogEntity log = new ScheduleJobLogEntity();
        log.setJobId(scheduleJob.getJobId());
        log.setBeanName(scheduleJob.getBeanName());
        log.setMethodName(scheduleJob.getMethodName());
        log.setParams(scheduleJob.getParams());
        log.setCreateTime(new Date());

        //任务开始时间
        long startTime = System.currentTimeMillis();

        try {
            //执行任务
            logger.info("任务准备执行，任务ID：" + scheduleJob.getJobId());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(),
                    scheduleJob.getMethodName(), scheduleJob.getParams());
            Future<?> future = service.submit(task);

            future.get();

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            log.setTimes((int) times);
            //任务状态    0：成功    1：失败
            log.setStatus(0);

            logger.info("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times + "毫秒");
        } catch (Exception e) {
            logger.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            log.setTimes((int) times);

            //任务状态    0：成功    1：失败
            log.setStatus(1);
            log.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            scheduleJobLogService.save(log);
        }
    }
}
