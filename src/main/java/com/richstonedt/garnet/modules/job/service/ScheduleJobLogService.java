/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.job.service;

import com.richstonedt.garnet.modules.job.entity.ScheduleJobLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 定时任务日志
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月1日 下午10:34:48
 * @since garnet-core-be-fe 1.0.0
 */
public interface ScheduleJobLogService {

	/**
	 * 根据ID，查询定时任务日志
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	ScheduleJobLogEntity queryObject(Long jobId);
	
	/**
	 * 查询定时任务日志列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	List<ScheduleJobLogEntity> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存定时任务日志
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	void save(ScheduleJobLogEntity log);
	
}
