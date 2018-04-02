package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.GroupMapper;
import com.richstonedt.garnet.model.Group;
import com.richstonedt.garnet.model.GroupRole;
import com.richstonedt.garnet.model.GroupUser;
import com.richstonedt.garnet.model.UserTenant;
import com.richstonedt.garnet.model.criteria.GroupCriteria;
import com.richstonedt.garnet.model.criteria.GroupRoleCriteria;
import com.richstonedt.garnet.model.criteria.GroupUserCriteria;
import com.richstonedt.garnet.model.criteria.UserTenantCriteria;
import com.richstonedt.garnet.model.parm.GroupParm;
import com.richstonedt.garnet.model.view.GroupView;
import com.richstonedt.garnet.model.view.ReturnTenantIdView;
import com.richstonedt.garnet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.w3c.dom.ls.LSInput;

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

    @Autowired
    private UserTenantService userTenantService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private CommonService commonService;


    @Autowired
    private UserService userService;

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

        System.out.println("创建组");

        //插入到组-用户中间表
        if (!ObjectUtils.isEmpty(groupView.getUserIds())) {
            List<Long> userIds = groupView.getUserIds();
            for (Long userId : userIds) {

                System.out.println("创建组userId："+userId);
                GroupUser groupUser = new GroupUser();
                groupUser.setId(IdGeneratorUtil.generateId());
                groupUser.setUserId(userId);
                groupUser.setGroupId(group.getId());
                groupUserService.insertSelective(groupUser);
            }
        }

        System.out.println("创建组后");

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

        //先删除关联 组-用户 外键
        GroupUserCriteria groupUserCriteria =  new GroupUserCriteria();
        groupUserCriteria.createCriteria().andGroupIdEqualTo(group.getId());
        groupUserService.deleteByCriteria(groupUserCriteria);

        //更新组-用户中间表
        if (!ObjectUtils.isEmpty(groupView.getUserIds())) {
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

        //先删除 组-角色 关联外键
        GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
        groupRoleCriteria.createCriteria().andGroupIdEqualTo(group.getId());
        groupRoleService.deleteByCriteria(groupRoleCriteria);

        if (!ObjectUtils.isEmpty(groupView.getRoleIds())) {
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
        GroupCriteria.Criteria criteria = groupCriteria.createCriteria();
        //只查询状态为1，即可见的
        criteria.andStatusEqualTo(1);
        criteria.andNameIsNotNull();

        if (!ObjectUtils.isEmpty(groupParm.getSearchName())) {
            criteria.andNameLike("%" + groupParm.getSearchName() + "%");
        }

        if(!ObjectUtils.isEmpty(groupParm.getUserId())){

            //根据userId 查tenantId
            ReturnTenantIdView returnTenantIdView = userService.getTenantIdsByUserId(groupParm.getUserId());
            List<Long> tenantIds = returnTenantIdView.getTenantIds();

            //如果不是garnet下的超级管理员，根据tenantId返回，否则返回所有group
            if (!returnTenantIdView.isSuperAdmin() || (returnTenantIdView.isSuperAdmin() && !commonService.superAdminBelongGarnet(groupParm.getUserId()))) {
                //根据tenantIds 查userIds
                List<Long> userIds = new ArrayList<>();
                UserTenantCriteria userTenantCriteria1 = new UserTenantCriteria();
                userTenantCriteria1.createCriteria().andTenantIdIn(tenantIds);
                List<UserTenant> userTenants1 = userTenantService.selectByCriteria(userTenantCriteria1);
                if (!CollectionUtils.isEmpty(userTenants1) && userTenants1.size() > 0) {
                    for (UserTenant userTenant : userTenants1) {
                        userIds.add(userTenant.getUserId());
                    }
                }

                //根据userIds 查group列表
                GroupUserCriteria groupUserCriteria = new GroupUserCriteria();
                groupUserCriteria.createCriteria().andUserIdIn(userIds);
                List<GroupUser> groupUsers = groupUserService.selectByCriteria(groupUserCriteria);
                if (!CollectionUtils.isEmpty(groupUsers) && groupUsers.size() > 0) {
                    List<Long> groupIds = new ArrayList<>();
                    for (GroupUser groupUser : groupUsers) {
                        groupIds.add(groupUser.getGroupId());
                    }
                    criteria.andIdIn(groupIds);
                } else {
                    return new PageUtil(null, 0,groupParm.getPageNumber() ,groupParm.getPageSize());
                }
            }

        }

        PageUtil result = new PageUtil(this.selectByCriteria(groupCriteria), (int)this.countByCriteria(groupCriteria),groupParm.getPageNumber() ,groupParm.getPageSize());
        return result;
    }

    @Override
    public void updateStatusById(Group group) {

        //先删除关联外键
        GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
        groupRoleCriteria.createCriteria().andGroupIdEqualTo(group.getId());
        groupRoleService.deleteByCriteria(groupRoleCriteria);

        GroupUserCriteria groupUserCriteria =  new GroupUserCriteria();
        groupUserCriteria.createCriteria().andGroupIdEqualTo(group.getId());
        groupUserService.deleteByCriteria(groupUserCriteria);

        Long currentTime = new Date().getTime();
        group.setModifiedTime(currentTime);
        group.setStatus(0);
        this.updateByPrimaryKeySelective(group);
    }

    @Override
    public List<Group> queryGroupsByTenantId(GroupParm groupParm) {
        Long tenantId = groupParm.getTenantId();
        GroupCriteria groupCriteria = new GroupCriteria();
        groupCriteria.createCriteria().andTenantIdEqualTo(tenantId).andStatusEqualTo(1);
        List<Group> groups = this.selectByCriteria(groupCriteria);


        return groups;

    }
}