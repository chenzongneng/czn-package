package com.richstonedt.garnet.service;

import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.SystemConfig;
import com.richstonedt.garnet.model.criteria.SystemConfigCriteria;
import com.richstonedt.garnet.model.parm.SystemConfigParm;
import com.richstonedt.garnet.model.view.SystemConfigView;

public interface SystemConfigService extends BaseService<SystemConfig, SystemConfigCriteria, Long> {

    Long insertSystemConfig(SystemConfigView systemConfigView);

    PageUtil<SystemConfig> querySystemConfigsByParms(SystemConfigParm systemConfigParm);

}