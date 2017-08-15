/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.job.service.impl;

import com.richstonedt.garnet.common.utils.Constant.ScheduleStatus;
import com.richstonedt.garnet.modules.job.dao.ScheduleJobDao;
import com.richstonedt.garnet.modules.job.entity.ScheduleJobEntity;
import com.richstonedt.garnet.modules.job.service.ScheduleJobService;
import com.richstonedt.garnet.modules.job.utils.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Schedule job service.
 *
 * @since garnet-core-be-fe 1.0.0
 */
@Service("scheduleJobService")
public class ScheduleJobServiceImpl implements ScheduleJobService {

	/**
	 * The Scheduler.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Autowired
    private Scheduler scheduler;

	/**
	 * The Scheduler job dao.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Autowired
	private ScheduleJobDao schedulerJobDao;
	
	/**
	 * 项目启动时，初始化定时器
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@PostConstruct
	public void init(){
		List<ScheduleJobEntity> scheduleJobList = schedulerJobDao.queryList(new HashMap<String, Object>());
		for(ScheduleJobEntity scheduleJob : scheduleJobList){
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            //如果不存在，则创建
            if(cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            }else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
		}
	}

	/**
	 * 根据ID，查询定时任务
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public ScheduleJobEntity queryObject(Long jobId) {
		return schedulerJobDao.queryObject(jobId);
	}

	/**
	 * 查询定时任务列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public List<ScheduleJobEntity> queryList(Map<String, Object> map) {
		return schedulerJobDao.queryList(map);
	}

	/**
	 * 查询总数
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public int queryTotal(Map<String, Object> map) {
		return schedulerJobDao.queryTotal(map);
	}

	/**
	 * 保存定时任务
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	@Transactional
	public void save(ScheduleJobEntity scheduleJob) {
		scheduleJob.setCreateTime(new Date());
		scheduleJob.setStatus(ScheduleStatus.NORMAL.getValue());
        schedulerJobDao.save(scheduleJob);
        
        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }

	/**
	 * 更新定时任务
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	@Transactional
	public void update(ScheduleJobEntity scheduleJob) {
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
                
        schedulerJobDao.update(scheduleJob);
    }

	/**
	 * 批量删除定时任务
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	@Transactional
    public void deleteBatch(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.deleteScheduleJob(scheduler, jobId);
    	}
    	
    	//删除数据
    	schedulerJobDao.deleteBatch(jobIds);
	}

	/**
	 * 批量更新定时任务状态
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
    public int updateBatch(Long[] jobIds, int status){
    	Map<String, Object> map = new HashMap<>();
    	map.put("list", jobIds);
    	map.put("status", status);
    	return schedulerJobDao.updateBatch(map);
    }

	/**
	 * 立即执行
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	@Transactional
    public void run(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.run(scheduler, queryObject(jobId));
    	}
    }

	/**
	 * 暂停运行
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	@Transactional
    public void pause(Long[] jobIds) {
        for(Long jobId : jobIds){
    		ScheduleUtils.pauseJob(scheduler, jobId);
    	}
        
    	updateBatch(jobIds, ScheduleStatus.PAUSE.getValue());
    }

	/**
	 * 恢复运行
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	@Transactional
    public void resume(Long[] jobIds) {
    	for(Long jobId : jobIds){
    		ScheduleUtils.resumeJob(scheduler, jobId);
    	}

    	updateBatch(jobIds, ScheduleStatus.NORMAL.getValue());
    }
    
}
