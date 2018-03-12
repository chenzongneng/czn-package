package com.richstonedt.garnet.model.parm;

import com.richstonedt.garnet.model.Application;

/**
 * The type Application parm.
 */
public class ApplicationParm extends BaseParm{

    private Application application;

    private String searchName;

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
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
