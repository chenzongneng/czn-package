package com.richstonedt.garnet.model.parm;

import com.richstonedt.garnet.model.Tenant;

/**
 * The type Tenant parm.
 */
public class TenantParm extends BaseParm {

    private Tenant tenant;

    private Long applicationId;


    /**
     * Gets tenant.
     *
     * @return the tenant
     */
    public Tenant getTenant() {
        return tenant;
    }

    /**
     * Sets tenant.
     *
     * @param tenant the tenant
     */
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }


    @Override
    public Long getApplicationId() {
        return applicationId;
    }

    @Override
    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }
}
