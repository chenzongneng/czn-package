package com.richstonedt.garnet.service;

import com.github.pagehelper.Page;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Group;
import com.richstonedt.garnet.model.criteria.GroupCriteria;
import com.richstonedt.garnet.model.parm.GroupParm;
import com.richstonedt.garnet.model.view.GroupView;

import java.util.List;

/**
 * The interface Group service.
 */
public interface GroupService extends BaseService<Group, GroupCriteria, Long> {


    /**
     * Insert group.
     *
     * @param groupView the group view
     */
    public Long insertGroup(GroupView groupView);

    /**
     * Update group.
     *
     * @param groupView the group view
     */
    public void updateGroup(GroupView groupView);

    /**
     * Delete group.
     *
     * @param groupView the group view
     */
    public void deleteGroup(GroupView groupView);


    public void deleteGroup(Long id);

    /**
     * 获取单个用户组以及其关联的userIds、roleIds
     * @param groupId
     * @return
     */
    GroupView selectGroupWithUserAndRole(Long groupId);

    /**
     * 获取租户列表
     * @param groupParm
     * @return
     */
    public PageUtil queryGroupsByParms(GroupParm groupParm);

    /**
     * 当用户点击删除时，将其状态设为禁用
     * @param group
     */
    public void updateStatusById(Group group);

    /**
     * 通过tenantId查询相对应的组
     * @param groupParm
     * @return
     */
    public List<Group> queryGroupsByTenantId(GroupParm groupParm);

    /**
     * 通过applicationId查询相对应的组
     * @param groupParm
     * @return
     */
    public List<Group> queryGroupsByApplicationId(GroupParm groupParm);

    /**
     * 通过applicationId或tenantId查询相对应的组
     * @param groupParm
     * @return
     */
    public List<Group> queryGroupsByParams(GroupParm groupParm);

    /**
     * Garnet组
     * @param userId
     * @return
     */
    List<Group> getGarnetGroupList(Long userId);

    /**
     * 查询组列表
     * @param groupParm
     * @return
     */
    PageUtil getGroupsByParams(GroupParm groupParm);

}