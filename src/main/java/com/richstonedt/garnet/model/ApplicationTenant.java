package com.richstonedt.garnet.model;

import java.io.Serializable;

/**
 *
 * TABLE:gar_application_tenants
 *
 * @mbg.generated 该代码为自动生成，请不要修改
 *
 * DATE: 2018-03-30 13:23
 */
public class ApplicationTenant implements Serializable {
    private Long id;

    private Long applicationId;

    private Long tenantId;

    /**
     * TABLE： gar_application_tenants
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * <br>
     *
     * TABLE： gar_application_tenants<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ApplicationTenant other = (ApplicationTenant) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()));
    }

    /**
     * <br>
     *
     * TABLE： gar_application_tenants<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        return result;
    }

    /**
     * <br>
     *
     * TABLE： gar_application_tenants<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-03-30 13:23
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", applicationId=").append(applicationId);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}