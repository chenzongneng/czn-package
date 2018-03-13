package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.GroupMapper;
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

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private GroupRoleService groupRoleService;

    @Override
    public BaseMapper getBaseMapper() {
        return this.groupMapper;
    }

    @Override
    public Long insertGroup(GroupView groupView) {

        //Group group = groupView.getGroup();
        Group group = groupView.getGroup();

        group.setId(IdGeneratorUtil.generateId());

        Long currentTime = new Date().getTime();

        group.setCreatedTime(currentTime);

        group.setModifiedTime(currentTime);

        this.insertSelective(group);

        //插入到组-用户中间表
        if (!ObjectUtils.isEmpty(groupView.getUserIds())) {
            List<Long> userIds = groupView.getUserIds();
            for (Long userId : userIds) {
                GroupUser groupUser = new GroupUser();
                groupUser.setId(IdGeneratorUtil.generateId());
                groupUser.setUserId(userId);
                groupUser.setGroupId(group.getId());
                groupUserService.insertSelective(groupUser);
            }
        }

        //插入到组-角色中间表
        if (!ObjectUtils.isEmpty(groupView.getRoleIds())) {
            List<Long> roleIds = groupView.getRoleIds();
            for (Long roleId : roleIds) {
                GroupRole groupRole = new GroupRole();
                groupRole.setId(IdGeneratorUtil.generateId());
                groupRole.setGroupId(group.getId());
                groupRole.setRoleId(roleId);
                groupRoleService.insertSelective(groupRole);
            }
        }

        /*if (!ObjectUtils.isEmpty(groupView.getGroupUsers())) {

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


        }*/

        return group.getId();


    }

    @Override
    public void updateGroup(GroupView groupView) {

        Group group = groupView.getGroup();
        Long currentTime = new Date().getTime();
        group.setModifiedTime(currentTime);
        this.updateByPrimaryKeySelective(group);

        if (ObjectUtils.isEmpty(group.getId())) {
            throw new RuntimeException("group's id can not be null");
        }

        //更新组-用户中间表
        if (!ObjectUtils.isEmpty(groupView.getUserIds())) {
            //先删除关联外键
            GroupUserCriteria groupUserCriteria =  new GroupUserCriteria();
            groupUserCriteria.createCriteria().andGroupIdEqualTo(group.getId());
            groupUserService.deleteByCriteria(groupUserCriteria);
            //插入更新关联信息
            List<Long> userIds = groupView.getUserIds();
            for (Long userId : userIds) {
                GroupUser groupUser = new GroupUser();
                groupUser.setUserId(userId);
                groupUser.setId(IdGeneratorUtil.generateId());
                groupUser.setGroupId(group.getId());
                groupUserService.insertSelective(groupUser);
            }
        }

        if (!ObjectUtils.isEmpty(groupView.getRoleIds())) {
            //先删除关联外键
            GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
            groupRoleCriteria.createCriteria().andGroupIdEqualTo(group.getId());
            groupRoleService.deleteByCriteria(groupRoleCriteria);
            //插入更新关联信息
            List<Long> roleIds = groupView.getRoleIds();
            for (Long roleId : roleIds) {
                GroupRole groupRole = new GroupRole();
                groupRole.setRoleId(roleId);
                groupRole.setGroupId(group.getId());
                groupRole.setId(IdGeneratorUtil.generateId());
                groupRoleService.insertSelective(groupRole);
            }
        }

        //更新组-角色中间表

        /*if (!ObjectUtils.isEmpty(groupView.getGroupUsers())) {

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


        }*/


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
    public void deleteGroup(Long id) {
        GroupUserCriteria groupUserCriteria = new GroupUserCriteria();
        groupUserCriteria.createCriteria().andGroupIdEqualTo(id);

        GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
        groupRoleCriteria.createCriteria().andGroupIdEqualTo(id);

        if (!ObjectUtils.isEmpty(groupUserService.selectByCriteria(groupUserCriteria))) {
            groupUserService.deleteByCriteria(groupUserCriteria);
        }

        if (!ObjectUtils.isEmpty(groupRoleService.selectByCriteria(groupRoleCriteria))) {
            groupRoleService.deleteByCriteria(groupRoleCriteria);
        }

        this.deleteByPrimaryKey(id);
    }

    @Override
    public GroupView selectGroupWithUserAndRole(Long groupId) {
        GroupView groupView = new GroupView();
        Group group =  this.selectByPrimaryKey(groupId);
        groupView.setGroup(group);

        //查询user列表
        GroupUserCriteria groupUserCriteria = new GroupUserCriteria();
        groupUserCriteria.createCriteria().andGroupIdEqualTo(groupId);
        List<GroupUser> groupUsers = groupUserService.selectByCriteria(groupUserCriteria);
        List<Long> userIds = new ArrayList<>();
        for (GroupUser groupUser : groupUsers) {
            userIds.add(groupUser.getUserId());
        }
        groupView.setUserIds(userIds);

        //查询role列表
        GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
        groupRoleCriteria.createCriteria().andGroupIdEqualTo(groupId);
        List<GroupRole> groupRoles = groupRoleService.selectByCriteria(groupRoleCriteria);
        List<Long> roleIds = new ArrayList<>();
        for (GroupRole groupRole : groupRoles) {
            roleIds.add(groupRole.getRoleId());
        }
        groupView.setRoleIds(roleIds);

        return groupView;
    }

    @Override
    public PageUtil queryGroupsByParms(GroupParm groupParm) {

        Group group = groupParm.getGroup();

        GroupCriteria groupCriteria = new GroupCriteria();
        //只查询状态为1，即可见的
        groupCriteria.createCriteria().andStatusEqualTo(1);
        groupCriteria.createCriteria().andNameIsNotNull();

        if(!ObjectUtils.isEmpty(groupParm.getApplicationId())){
            groupCriteria.createCriteria().andApplicationIdEqualTo(groupParm.getApplicationId());
        }
        if(!ObjectUtils.isEmpty(groupParm.getTenantId())){
            groupCriteria.createCriteria().andTenantIdEqualTo(groupParm.getTenantId());
        }

        if (!ObjectUtils.isEmpty(groupParm.getSearchName())) {
            groupCriteria.createCriteria().andNameLike("%" + groupParm.getSearchName() + "%");
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

        PageUtil result = new PageUtil(this.selectByCriteria(groupCriteria), (int)this.countByCriteria(groupCriteria),groupParm.getPageNumber() ,groupParm.getPageSize());


        return result;
    }

    @Override
    public void updateStatusById(Group group) {
        Long currentTime = new Date().getTime();
        group.setModifiedTime(currentTime);
        group.setStatus(0);
        this.updateByPrimaryKeySelective(group);
    }
}