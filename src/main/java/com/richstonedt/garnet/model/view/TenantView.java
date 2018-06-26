package com.richstonedt.garnet.model.view;

import com.richstonedt.garnet.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * The type Tenant view.
 */
public class TenantView {

    private Tenant tenant;

    private List<ApplicationTenant> applicationTenants;

    private List<UserTenant> userTenants;

    private String userName;

    private Integer modeId;

    @ApiModelProperty(value = "关联的用户名，用“,”隔开")
    private String userNames;

    @ApiModelProperty(value = "要解除关联的用户名，用“,”隔开")
    private String delRelatedUserNames;

    @ApiModelProperty(value = "登录用户Id")
    private Long loginUserId;

    public Long getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(Long loginUserId) {
        this.loginUserId = loginUserId;
    }

    public String getDelRelatedUserNames() {
        return delRelatedUserNames;
    }

    public void setDelRelatedUserNames(String delRelatedUserNames) {
        this.delRelatedUserNames = delRelatedUserNames;
    }

    public String getUserNames() {
        return userNames;
    }

    public void setUserNames(String userNames) {
        this.userNames = userNames;
    }

    public Integer getModeId() {
        return modeId;
    }

    public void setModeId(Integer modeId) {
        this.modeId = modeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * The App name list.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用名称列表")
    private List<String> appNameList;

    /**
     * The App id list.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用ID列表")
    private List<Long> appIdList;


    /**
     * The App ids.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @ApiModelProperty(value = "应用ID，用‘，’隔开")
    private String appIds;

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

    /**
     * Gets application tenants.
     *
     * @return the application tenants
     */
    public List<ApplicationTenant> getApplicationTenants() {
        return applicationTenants;
    }

    /**
     * Sets application tenants.
     *
     * @param applicationTenants the application tenants
     */
    public void setApplicationTenants(List<ApplicationTenant> applicationTenants) {
        this.applicationTenants = applicationTenants;
    }

    /**
     * Gets user tenants.
     *
     * @return the user tenants
     */
    public List<UserTenant> getUserTenants() {
        return userTenants;
    }

    /**
     * Sets user tenants.
     *
     * @param userTenants the user tenants
     */
    public void setUserTenants(List<UserTenant> userTenants) {
        this.userTenants = userTenants;
    }

    public List<String> getAppNameList() {
        return appNameList;
    }

    public void setAppNameList(List<String> appNameList) {
        this.appNameList = appNameList;
    }

    public List<Long> getAppIdList() {
        return appIdList;
    }

    public void setAppIdList(List<Long> appIdList) {
        this.appIdList = appIdList;
    }

    public String getAppIds() {
        return appIds;
    }

    public void setAppIds(String appIds) {
        this.appIds = appIds;
    }
}
