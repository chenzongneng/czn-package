package com.richstonedt.garnet.model;

import java.io.Serializable;

/**
 *
 * TABLE:gar_applications
 *
 * @mbg.generated 该代码为自动生成，请不要修改
 *
 * DATE: 2018-02-26 01:15
 */
public class Application implements Serializable {
    private Long id;

    private String name;

    private String company;

    private String appCode;

    private String refreshResourcesApi;

    private String hosts;

    private Long createdTime;

    private Long modifiedTime;

    /**
     * TABLE： gar_applications
     *
     * @mbg.generated
     *
     * DATE: 2018-02-26 01:15
     */
    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    public String getRefreshResourcesApi() {
        return refreshResourcesApi;
    }

    public void setRefreshResourcesApi(String refreshResourcesApi) {
        this.refreshResourcesApi = refreshResourcesApi == null ? null : refreshResourcesApi.trim();
    }

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts == null ? null : hosts.trim();
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-02-26 01:15
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
        Application other = (Application) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCompany() == null ? other.getCompany() == null : this.getCompany().equals(other.getCompany()))
            && (this.getAppCode() == null ? other.getAppCode() == null : this.getAppCode().equals(other.getAppCode()))
            && (this.getRefreshResourcesApi() == null ? other.getRefreshResourcesApi() == null : this.getRefreshResourcesApi().equals(other.getRefreshResourcesApi()))
            && (this.getHosts() == null ? other.getHosts() == null : this.getHosts().equals(other.getHosts()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getModifiedTime() == null ? other.getModifiedTime() == null : this.getModifiedTime().equals(other.getModifiedTime()));
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-02-26 01:15
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCompany() == null) ? 0 : getCompany().hashCode());
        result = prime * result + ((getAppCode() == null) ? 0 : getAppCode().hashCode());
        result = prime * result + ((getRefreshResourcesApi() == null) ? 0 : getRefreshResourcesApi().hashCode());
        result = prime * result + ((getHosts() == null) ? 0 : getHosts().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getModifiedTime() == null) ? 0 : getModifiedTime().hashCode());
        return result;
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-02-26 01:15
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", company=").append(company);
        sb.append(", appCode=").append(appCode);
        sb.append(", refreshResourcesApi=").append(refreshResourcesApi);
        sb.append(", hosts=").append(hosts);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", modifiedTime=").append(modifiedTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}