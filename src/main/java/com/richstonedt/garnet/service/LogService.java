package com.richstonedt.garnet.service;

import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Log;
import com.richstonedt.garnet.model.criteria.LogCriteria;
import com.richstonedt.garnet.model.parm.LogParm;

public interface LogService extends BaseService<Log, LogCriteria, Long> {

    /**
     * 查询系统日志列表
     * @param logParm
     * @return
     */
    PageUtil queryLogsByParms(LogParm logParm);

}