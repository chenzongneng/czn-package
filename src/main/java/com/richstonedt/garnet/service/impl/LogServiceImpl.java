package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.LogMapper;
import com.richstonedt.garnet.model.Log;
import com.richstonedt.garnet.model.criteria.LogCriteria;
import com.richstonedt.garnet.model.parm.LogParm;
import com.richstonedt.garnet.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class LogServiceImpl extends BaseServiceImpl<Log, LogCriteria, Long> implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return this.logMapper;
    }

    @Override
    public PageUtil queryLogsByParms(LogParm logParm) {

        LogCriteria logCriteria = new LogCriteria();
        logCriteria.setOrderByClause(GarnetContants.ORDER_BY_CREATED_TIME);
        LogCriteria.Criteria criteria = logCriteria.createCriteria();

        String searchName = logParm.getSearchName();
        if (!StringUtils.isEmpty(searchName)) {
            criteria.andUserNameLike("%" + searchName + "%");
        }

        return new PageUtil(this.selectByCriteria(logCriteria), (int)this.countByCriteria(logCriteria) ,logParm.getPageSize(), logParm.getPageNumber());
    }
}