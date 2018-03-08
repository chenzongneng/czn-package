package com.richstonedt.garnet.service;

import com.github.pagehelper.PageInfo;
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


    /**
     * 获取租户列表
     * @param groupParm
     * @return
     */
    public PageUtil queryGroupsByParms(GroupParm groupParm);


}