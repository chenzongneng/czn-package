package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.SystemConfigMapper;
import com.richstonedt.garnet.model.SystemConfig;
import com.richstonedt.garnet.model.criteria.SystemConfigCriteria;
import com.richstonedt.garnet.model.parm.SystemConfigParm;
import com.richstonedt.garnet.model.view.SystemConfigView;
import com.richstonedt.garnet.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SystemConfigServiceImpl extends BaseServiceImpl<SystemConfig, SystemConfigCriteria, Long> implements SystemConfigService {
    @Autowired
    private SystemConfigMapper systemConfigMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return this.systemConfigMapper;
    }

    @Override
    public Long insertSystemConfig(SystemConfigView systemConfigView) {
        SystemConfig systemConfig = systemConfigView.getSystemConfig();
        systemConfig.setId(IdGeneratorUtil.generateId());
        return systemConfig.getId();
    }

    @Override
    public PageUtil<SystemConfig> querySystemConfigsByParms(SystemConfigParm systemConfigParm) {
        return null;
    }
}