package com.richstonedt.garnet.model.parm;

import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.SystemConfig;

/**
 * The type Application parm.
 */
public class SystemConfigParm extends BaseParm{

    private SystemConfig systemConfig;

    private Integer modeId;

    public SystemConfig getSystemConfig() {
        return systemConfig;
    }

    public void setSystemConfig(SystemConfig systemConfig) {
        this.systemConfig = systemConfig;
    }

    public Integer getModeId() {
        return modeId;
    }

    public void setModeId(Integer modeId) {
        this.modeId = modeId;
    }
}
