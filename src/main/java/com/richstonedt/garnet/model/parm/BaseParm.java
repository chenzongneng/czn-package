package com.richstonedt.garnet.model.parm;

import io.swagger.annotations.ApiModelProperty;

/**
 * The type Base parm.
 */
public class BaseParm {

    private int pageSize;

    private int pageNumber;

    private Long userId;

    private Long tenantId;

    private Long applicationId;

    @ApiModelProperty(value = "根据名字搜索")
    private String searchName;

    @ApiModelProperty(value = "登录用户Id")
    private Long loginUserId;

    public Long getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(Long loginUserId) {
        this.loginUserId = loginUserId;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    /**
     * Gets page size.
     *
     * @return the page size
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets page size.
     *
     * @param pageSize the page size
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Gets page number.
     *
     * @return the page number
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets page number.
     *
     * @param pageNumber the page number
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets tenant id.
     *
     * @return the tenant id
     */
    public Long getTenantId() {
        return tenantId;
    }

    /**
     * Sets tenant id.
     *
     * @param tenantId the tenant id
     */
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * Gets application id.
     *
     * @return the application id
     */
    public Long getApplicationId() {
        return applicationId;
    }

    /**
     * Sets application id.
     *
     * @param applicationId the application id
     */
    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }
}
