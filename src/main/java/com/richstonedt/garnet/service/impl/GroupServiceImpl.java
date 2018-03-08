package com.richstonedt.garnet.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.GroupMapper;
import com.richstonedt.garnet.model.Application;
import com.richstonedt.garnet.model.Group;
import com.richstonedt.garnet.model.GroupRole;
import com.richstonedt.garnet.model.GroupUser;
import com.richstonedt.garnet.model.criteria.GroupCriteria;
import com.richstonedt.garnet.model.criteria.GroupRoleCriteria;
import com.richstonedt.garnet.model.criteria.GroupUserCriteria;
import com.richstonedt.garnet.model.parm.GroupParm;
import com.richstonedt.garnet.model.view.GroupView;
import com.richstonedt.garnet.service.GroupRoleService;
import com.richstonedt.garnet.service.GroupService;
import com.richstonedt.garnet.service.GroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class GroupServiceImpl extends BaseServiceImpl<Group, GroupCriteria, Long> implements GroupService {
    @Autowired
    private GroupMapper groupMapper;

    private GroupUserService groupUserService;

    private GroupRoleService groupRoleService;

    @Override
    public BaseMapper getBaseMapper() {
        return this.groupMapper;
    }

    @Override
    public Long insertGroup(GroupView groupView) {

        Group group = groupView.getGroup();

        group.setId(IdGeneratorUtil.generateId());

        Long currentTime = new Date().getTime();

        group.setCreatedTime(currentTime);

        group.setModifiedTime(currentTime);

        this.insertSelective(group);

        if (!ObjectUtils.isEmpty(groupView.getGroupUsers())) {

            for (GroupUser groupUser :
                    groupView.getGroupUsers()) {

                groupUser.setId(IdGeneratorUtil.generateId());
                groupUser.setGroupId(group.getId());
                groupUserService.insertSelective(groupUser);

            }
        }

        if (!ObjectUtils.isEmpty(groupView.getGroupRoles())) {

            for (GroupRole groupRole : groupView.getGroupRoles()
                    ) {

                groupRole.setId(IdGeneratorUtil.generateId());
                groupRole.setGroupId(group.getId());
                groupRoleService.insertSelective(groupRole);
            }


        }

        return group.getId();


    }

    @Override
    public void updateGroup(GroupView groupView) {

        Group group = groupView.getGroup();
        Long currentTime = new Date().getTime();
        group.setModifiedTime(currentTime);
        this.updateByPrimaryKeySelective(group);

        if (!ObjectUtils.isEmpty(groupView.getGroupUsers())) {

            GroupUserCriteria groupUserCriteria = new GroupUserCriteria();
            groupUserCriteria.createCriteria().andGroupIdEqualTo(group.getId());
            groupUserService.deleteByCriteria(groupUserCriteria);

            for (GroupUser groupUser :
                    groupView.getGroupUsers()) {

                groupUser.setId(IdGeneratorUtil.generateId());
                groupUser.setGroupId(group.getId());
                groupUserService.insertSelective(groupUser);

            }
        }

        if (!ObjectUtils.isEmpty(groupView.getGroupRoles())) {

            GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
            groupRoleCriteria.createCriteria().andGroupIdEqualTo(group.getId());
            groupRoleService.deleteByCriteria(groupRoleCriteria);

            for (GroupRole groupRole : groupView.getGroupRoles()
                    ) {

                groupRole.setId(IdGeneratorUtil.generateId());
                groupRole.setGroupId(group.getId());
                groupRoleService.insertSelective(groupRole);
            }


        }


    }

    @Override
    public void deleteGroup(GroupView groupView) {

        Group group = groupView.getGroup();

        if (!ObjectUtils.isEmpty(groupView.getGroupUsers())) {

            GroupUserCriteria groupUserCriteria = new GroupUserCriteria();
            groupUserCriteria.createCriteria().andGroupIdEqualTo(group.getId());
            groupUserService.deleteByCriteria(groupUserCriteria);
        }

        if (!ObjectUtils.isEmpty(groupView.getGroupRoles())) {

            GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
            groupRoleCriteria.createCriteria().andGroupIdEqualTo(group.getId());
            groupRoleService.deleteByCriteria(groupRoleCriteria);

        }

        this.deleteByPrimaryKey(group.getId());

    }

    @Override
    public PageUtil queryGroupsByParms(GroupParm groupParm) {

        Group group = groupParm.getGroup();

        GroupCriteria groupCriteria = new GroupCriteria();
        groupCriteria.createCriteria().andNameIsNotNull();

        if(!ObjectUtils.isEmpty(groupParm.getApplicationId())){

            groupCriteria.createCriteria().andApplicationIdEqualTo(groupParm.getApplicationId());

        }
        if(!ObjectUtils.isEmpty(groupParm.getTenantId())){

            groupCriteria.createCriteria().andTenantIdEqualTo(groupParm.getTenantId());
        }
//        if(!ObjectUtils.isEmpty(groupParm.getUserId())){
//
//            GroupUserCriteria groupUserCriteria = new GroupUserCriteria();
//
//            groupUserCriteria.createCriteria().andUserIdEqualTo(groupParm.getUserId());
//
//            List<GroupUser> groupUsers = groupUserService.selectByCriteria(groupUserCriteria);
//
//            List<Long> groupUserIds = new ArrayList<>();
//
//            for (Long groupUserId : groupUserIds){
//
//                groupUserIds.add(groupUserId);
//
//            }
//
//            groupCriteria.createCriteria().andIdIn(groupUserIds);
//
//        }


        System.out.println("this.selectByCriteria(groupCriteria).size()"+ this.selectByCriteria(groupCriteria).size());
        PageUtil result = new PageUtil(this.selectByCriteria(groupCriteria), (int)this.countByCriteria(groupCriteria),groupParm.getPageNumber() ,groupParm.getPageSize());


        return result;
    }
}