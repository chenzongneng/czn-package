/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.job.dao;

import com.richstonedt.garnet.modules.job.entity.ScheduleJobEntity;
import com.richstonedt.garnet.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 定时任务
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月1日 下午10:29:57
 * @since garnet-core-be-fe 1.0.0
 */
@Mapper
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {

    /**
     * 批量更新状态
     *
     * @since garnet-core-be-fe 1.0.0
     */
    int updateBatch(Map<String, Object> map);

}
