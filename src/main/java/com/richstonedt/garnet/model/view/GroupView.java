package com.richstonedt.garnet.model.view;

import com.richstonedt.garnet.model.Group;
import com.richstonedt.garnet.model.GroupRole;
import com.richstonedt.garnet.model.GroupUser;

import java.util.List;

/**
 * The type Group view.
 */
public class GroupView {

    private Group group;

    private List<GroupUser> groupUsers;

    private List<GroupRole> groupRoles;

    private List<Long> userIds;

    private List<Long> roleIds;

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    /**
     * Gets group.
     *
     * @return the group
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Sets group.
     *
     * @param group the group
     */
    public void setGroup(Group group) {
        this.group = group;
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
     * Gets group roles.
     *
     * @return the group roles
     */
    public List<GroupRole> getGroupRoles() {
        return groupRoles;
    }

    /**
     * Sets group roles.
     *
     * @param groupRoles the group roles
     */
    public void setGroupRoles(List<GroupRole> groupRoles) {
        this.groupRoles = groupRoles;
    }

}
