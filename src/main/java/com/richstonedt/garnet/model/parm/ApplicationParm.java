package com.richstonedt.garnet.model.parm;

import com.richstonedt.garnet.model.Application;

/**
 * The type Application parm.
 */
public class ApplicationParm extends BaseParm{

    private Application application;

    private String mode;

    private Integer modeId;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Integer getModeId() {
        return modeId;
    }

    public void setModeId(Integer modeId) {
        this.modeId = modeId;
    }

    /**
     * Gets application.
     *
     * @return the application
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Sets application.
     *
     * @param application the application
     */
    public void setApplication(Application application) {
        this.application = application;
    }

}
