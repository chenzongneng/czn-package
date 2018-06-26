package com.richstonedt.garnet.model.view;

import com.richstonedt.garnet.model.GroupUser;
import com.richstonedt.garnet.model.User;
import com.richstonedt.garnet.model.UserTenant;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * The type User view.
 */
public class UserView {

    private User user;

    @ApiModelProperty(value = "登录用户Id")
    private Long loginUserId;

    private List<UserTenant> userTenants;

    private List<GroupUser> groupUsers;

    @ApiModelProperty(value = "密码")
    private String password;

    private Long expiredDateTime;

    @ApiModelProperty(value = "选择关联的租户ID")
    private List<Long> tenantIds;

    @ApiModelProperty(value = "选择关联的组ID")
    private List<Long> groupIds;


    public Long getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(Long loginUserId) {
        this.loginUserId = loginUserId;
    }

    public List<Long> getTenantIds() {
        return tenantIds;
    }

    public void setTenantIds(List<Long> tenantIds) {
        this.tenantIds = tenantIds;
    }

    public List<Long> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Long> groupIds) {
        this.groupIds = groupIds;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
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

    /**
     * Gets group users.
     *
     * @return the group users
     */
    public List<GroupUser> getGroupUsers() {
        return groupUsers;
    }

    /**
     * Sets group users.
     *
     * @param groupUsers the group users
     */
    public void setGroupUsers(List<GroupUser> groupUsers) {
        this.groupUsers = groupUsers;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets expired date time.
     *
     * @return the expired date time
     */
    public Long getExpiredDateTime() {
        return expiredDateTime;
    }

    /**
     * Sets expired date time.
     *
     * @param expiredDateTime the expired date time
     */
    public void setExpiredDateTime(Long expiredDateTime) {
        this.expiredDateTime = expiredDateTime;
    }
}
