package com.richstonedt.garnet.model;

import java.io.Serializable;

/**
 *
 * TABLE:gar_applications
 *
 * @mbg.generated 该代码为自动生成，请不要修改
 *
 * DATE: 2018-06-05 12:21
 */
public class Application implements Serializable {
    private Long id;

    private String name;

    private String company;

    /**
     * 调用接口时应用唯一标识
     * gar_applications.app_code
     */
    private String appCode;

    private String refreshResourcesApi;

    /**
     * 此application所在的所有ip:ports，用分号分隔。如: 192.168.6.97:8080;192.168.6.98:8080

     加入已经作为微服务加入服务注册中心，无需填写此字段。系统会自动把app_code当做服务id，通过负载均衡器在服务中心找到对应服务。
     * gar_applications.hosts
     */
    private String hosts;

    private Long createdTime;

    private Long modifiedTime;

    private Integer status;

    /**
     * 属于paas服务还是saas服务
     * gar_applications.service_mode
     */
    private String serviceMode;

    /**
     * 更新的人
     * gar_applications.updated_by_user_name
     */
    private String updatedByUserName;

    private String defaultIndexUrl;

    /**
     * TABLE： gar_applications
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
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

    /**
     * 调用接口时应用唯一标识<br>
     *
     * column：gar_applications.app_code<br>
     * @return app_code
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public String getAppCode() {
        return appCode;
    }

    /**
     * 调用接口时应用唯一标识<br>
     *
     * column：gar_applications.app_code<br>
     * @param appCode
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    public String getRefreshResourcesApi() {
        return refreshResourcesApi;
    }

    public void setRefreshResourcesApi(String refreshResourcesApi) {
        this.refreshResourcesApi = refreshResourcesApi == null ? null : refreshResourcesApi.trim();
    }

    /**
     * 此application所在的所有ip:ports，用分号分隔。如: 192.168.6.97:8080;192.168.6.98:8080

     加入已经作为微服务加入服务注册中心，无需填写此字段。系统会自动把app_code当做服务id，通过负载均衡器在服务中心找到对应服务。<br>
     *
     * column：gar_applications.hosts<br>
     * @return hosts
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public String getHosts() {
        return hosts;
    }

    /**
     * 此application所在的所有ip:ports，用分号分隔。如: 192.168.6.97:8080;192.168.6.98:8080

     加入已经作为微服务加入服务注册中心，无需填写此字段。系统会自动把app_code当做服务id，通过负载均衡器在服务中心找到对应服务。<br>
     *
     * column：gar_applications.hosts<br>
     * @param hosts
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 属于paas服务还是saas服务<br>
     *
     * column：gar_applications.service_mode<br>
     * @return service_mode
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public String getServiceMode() {
        return serviceMode;
    }

    /**
     * 属于paas服务还是saas服务<br>
     *
     * column：gar_applications.service_mode<br>
     * @param serviceMode
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public void setServiceMode(String serviceMode) {
        this.serviceMode = serviceMode == null ? null : serviceMode.trim();
    }

    /**
     * 更新的人<br>
     *
     * column：gar_applications.updated_by_user_name<br>
     * @return updated_by_user_name
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public String getUpdatedByUserName() {
        return updatedByUserName;
    }

    /**
     * 更新的人<br>
     *
     * column：gar_applications.updated_by_user_name<br>
     * @param updatedByUserName
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
     */
    public void setUpdatedByUserName(String updatedByUserName) {
        this.updatedByUserName = updatedByUserName == null ? null : updatedByUserName.trim();
    }

    public String getDefaultIndexUrl() {
        return defaultIndexUrl;
    }

    public void setDefaultIndexUrl(String defaultIndexUrl) {
        this.defaultIndexUrl = defaultIndexUrl == null ? null : defaultIndexUrl.trim();
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
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
                && (this.getModifiedTime() == null ? other.getModifiedTime() == null : this.getModifiedTime().equals(other.getModifiedTime()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getServiceMode() == null ? other.getServiceMode() == null : this.getServiceMode().equals(other.getServiceMode()))
                && (this.getUpdatedByUserName() == null ? other.getUpdatedByUserName() == null : this.getUpdatedByUserName().equals(other.getUpdatedByUserName()))
                && (this.getDefaultIndexUrl() == null ? other.getDefaultIndexUrl() == null : this.getDefaultIndexUrl().equals(other.getDefaultIndexUrl()));
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
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
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getServiceMode() == null) ? 0 : getServiceMode().hashCode());
        result = prime * result + ((getUpdatedByUserName() == null) ? 0 : getUpdatedByUserName().hashCode());
        result = prime * result + ((getDefaultIndexUrl() == null) ? 0 : getDefaultIndexUrl().hashCode());
        return result;
    }

    /**
     * <br>
     *
     * TABLE： gar_applications<br>
     *
     * @mbg.generated
     *
     * DATE: 2018-06-05 12:21
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
        sb.append(", status=").append(status);
        sb.append(", serviceMode=").append(serviceMode);
        sb.append(", updatedByUserName=").append(updatedByUserName);
        sb.append(", defaultIndexUrl=").append(defaultIndexUrl);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}