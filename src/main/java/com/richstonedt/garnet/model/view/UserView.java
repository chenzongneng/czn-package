package com.richstonedt.garnet.model.view;

import com.richstonedt.garnet.model.GroupUser;
import com.richstonedt.garnet.model.User;
import com.richstonedt.garnet.model.UserTenant;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * The type User view.
 */
public class UserView {

    private User user;

    private List<UserTenant> userTenants;

    private List<GroupUser> groupUsers;

    @ApiModelProperty(value = "密码")
    private String password;

    private Long expiredDateTime;

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
