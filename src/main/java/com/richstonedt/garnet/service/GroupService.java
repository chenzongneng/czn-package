package com.richstonedt.garnet.service;

import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Group;
import com.richstonedt.garnet.model.criteria.GroupCriteria;
import com.richstonedt.garnet.model.parm.GroupParm;
import com.richstonedt.garnet.model.view.GroupView;

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

    public GroupView selectGroupWithUserAndRole(Long groupId);

    /**
     * 获取租户列表
     * @param groupParm
     * @return
     */
    public PageUtil queryGroupsByParms(GroupParm groupParm);

    /**
     * 当用户点击删除时，将其状态设为禁用
     * @param id
     */
    public void updateGroupEnabled(Long id);


}