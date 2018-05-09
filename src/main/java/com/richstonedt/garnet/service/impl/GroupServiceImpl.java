package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.contants.GarnetContants;
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

        Group group = groupView.getGroup();

        group.setId(IdGeneratorUtil.generateId());

        Long currentTime = System.currentTimeMillis();

        group.setCreatedTime(currentTime);

        group.setModifiedTime(currentTime);

        //验证组名称是否已经存在
        checkDuplicateGroupName(group);

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

        return group.getId();


    }

    @Override
    public void updateGroup(GroupView groupView) {

        Group group = groupView.getGroup();
        Long currentTime = System.currentTimeMillis();
        group.setModifiedTime(currentTime);

        //验证组名称是否已经存在
        checkDuplicateGroupName(group);

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

        GroupCriteria groupCriteria = new GroupCriteria();
        groupCriteria.setOrderByClause(GarnetContants.ORDER_BY_CREATED_TIME);
        GroupCriteria.Criteria criteria = groupCriteria.createCriteria();
        //只查询状态为1，即可见的
        criteria.andStatusEqualTo(1);

        if (!ObjectUtils.isEmpty(groupParm.getSearchName())) {
            criteria.andNameLike("%" + groupParm.getSearchName() + "%");
        }

        if(!ObjectUtils.isEmpty(groupParm.getUserId())){
            //根据userId 查tenantId
            ReturnTenantIdView returnTenantIdView = userService.getTenantIdsByUserId(groupParm.getUserId());

            //如果不是garnet下的超级管理员，根据tenantId返回，否则返回所有group
            if (!returnTenantIdView.isSuperAdmin() || (returnTenantIdView.isSuperAdmin() && !commonService.superAdminBelongGarnet(groupParm.getUserId()))) {
                //根据userId 查group列表
                List<Long> groupIdList = this.getGroupsByUserId(groupParm.getUserId());

                if (CollectionUtils.isEmpty(groupIdList) || groupIdList.size() == 0) {
                    return new PageUtil(null, 0 ,groupParm.getPageSize(), groupParm.getPageNumber());
                } else {
                    criteria.andIdIn(groupIdList);
                }
            }
        }

        PageUtil result = new PageUtil(this.selectByCriteria(groupCriteria), (int)this.countByCriteria(groupCriteria) ,groupParm.getPageSize(), groupParm.getPageNumber());
        return result;
    }

    /**
     * 根据userId查询groupId列表
     * @param userId
     * @return
     */
    private List<Long> getGroupsByUserId(Long userId) {
        GroupUserCriteria groupUserCriteria = new GroupUserCriteria();
        groupUserCriteria.createCriteria().andUserIdEqualTo(userId);
        List<GroupUser> groupUsers = groupUserService.selectByCriteria(groupUserCriteria);
        if (!CollectionUtils.isEmpty(groupUsers) && groupUsers.size() > 0) {
            List<Long> groupIds = new ArrayList<>();
            for (GroupUser groupUser : groupUsers) {
                groupIds.add(groupUser.getGroupId());
            }

            List<Long> groupIdList = commonService.dealGroupIdsIfGarnet(userId, groupIds);

            if (groupIdList.size() == 0) {
                groupIdList.add(GarnetContants.NON_VALUE);
            }
            return groupIdList;
        } else {
            return new ArrayList<>();
        }

    }

    @Override
    public void updateStatusById(Group group) {

        if (GarnetContants.GARNET_GROUP_ID.longValue() == group.getId().longValue()) {
            throw new RuntimeException("不能删除超级组");
        }

        //先删除关联外键
        GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
        groupRoleCriteria.createCriteria().andGroupIdEqualTo(group.getId());
        groupRoleService.deleteByCriteria(groupRoleCriteria);

        GroupUserCriteria groupUserCriteria =  new GroupUserCriteria();
        groupUserCriteria.createCriteria().andGroupIdEqualTo(group.getId());
        groupUserService.deleteByCriteria(groupUserCriteria);

        Long currentTime = System.currentTimeMillis();
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

    @Override
    public List<Group> queryGroupsByApplicationId(GroupParm groupParm) {

        Long applicationId = groupParm.getApplicationId();

        GroupCriteria groupCriteria = new GroupCriteria();
        groupCriteria.createCriteria().andApplicationIdEqualTo(applicationId).andStatusEqualTo(1);
        List<Group> groups = this.selectByCriteria(groupCriteria);

        return groups;
    }

    @Override
    public List<Group> queryGroupsByParams(GroupParm groupParm) {
        Long applicationId = groupParm.getApplicationId();
        Long tenantId = groupParm.getTenantId();

        GroupCriteria groupCriteria = new GroupCriteria();
        GroupCriteria.Criteria criteria = groupCriteria.createCriteria();
        criteria.andStatusEqualTo(1);

        if (!ObjectUtils.isEmpty(applicationId) && applicationId.longValue() != 0) {
            criteria.andApplicationIdEqualTo(applicationId);
        }

        if (!ObjectUtils.isEmpty(tenantId) && tenantId.longValue() != 0) {
            criteria.andTenantIdEqualTo(tenantId);
        }

        List<Group> groups = this.selectByCriteria(groupCriteria);
        return groups;
    }

    /**
     * 验证组名称是否已经存在
     */
    private void checkDuplicateGroupName(Group group) {

        Long id = group.getId();
        String name = group.getName();

        GroupCriteria groupCriteria = new GroupCriteria();
        groupCriteria.createCriteria().andNameEqualTo(name).andStatusEqualTo(1);
        Group group1 = this.selectSingleByCriteria(groupCriteria);

        if (!ObjectUtils.isEmpty(group1) && group1.getId().longValue() != id.longValue()) {
            throw new RuntimeException("组名称已被使用");
        }

    }
}