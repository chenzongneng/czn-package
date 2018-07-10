package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.interceptory.LogRequired;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.GroupMapper;
import com.richstonedt.garnet.model.*;
import com.richstonedt.garnet.model.criteria.*;
import com.richstonedt.garnet.model.parm.GroupParm;
import com.richstonedt.garnet.model.view.GroupView;
import com.richstonedt.garnet.model.view.ReturnTenantIdView;
import com.richstonedt.garnet.service.*;
import com.sun.jdi.IntegerValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.w3c.dom.ls.LSInput;

import java.util.*;

@Service
@Transactional
public class GroupServiceImpl extends BaseServiceImpl<Group, GroupCriteria, Long> implements GroupService {
    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private GroupUserService groupUserService;

    @Autowired
    private GroupRoleService groupRoleService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private  UserTenantService userTenantService;

    @Autowired
    private TenantService tenantService;
    @Override
    public BaseMapper getBaseMapper() {
        return this.groupMapper;
    }

    @LogRequired(module = "组管理模块", method = "新增组")
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
            Log log = new Log();
            log.setMessage("组管理模块");
            log.setOperation("组绑定用户");
            commonService.insertLog(log);
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
            Log log = new Log();
            log.setMessage("组管理模块");
            log.setOperation("组绑定角色");
            commonService.insertLog(log);
        }

        return group.getId();


    }

    @LogRequired(module = "组管理模块", method = "配置组")
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
            Log log = new Log();
            log.setMessage("组管理模块");
            log.setOperation("组绑定用户");
            commonService.insertLog(log);
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
            Log log = new Log();
            log.setMessage("组管理模块");
            log.setOperation("组绑定角色");
            commonService.insertLog(log);
        }

    }

    @LogRequired(module = "组管理模块", method = "删除组")
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

    @LogRequired(module = "组管理模块", method = "删除组")
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

        if (!ObjectUtils.isEmpty(groupParm.getApplicationId())) {
            criteria.andApplicationIdEqualTo(groupParm.getApplicationId());
        }

        if (!ObjectUtils.isEmpty(groupParm.getTenantId())) {
            criteria.andTenantIdEqualTo(groupParm.getTenantId());
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

        List<Group> groupList = this.selectByCriteria(groupCriteria);
        GroupView groupView;
        List<GroupView> groupViewList = new ArrayList<>();
        for (Group group : groupList) {
            groupView = new GroupView();
            groupView.setGroup(group);
            if (group.getTenantId().longValue() != 0 && group.getApplicationId().longValue() == 0) {
                groupView.setType("租户");
            } else if (group.getTenantId().longValue() == 0 && group.getApplicationId().longValue() != 0) {
                groupView.setType("应用");
            } else {
                groupView.setType("租户+应用");
            }

            groupViewList.add(groupView);
        }


        PageUtil result = new PageUtil(groupViewList, (int)this.countByCriteria(groupCriteria) ,groupParm.getPageSize(), groupParm.getPageNumber());
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

    @LogRequired(module = "组管理模块", method = "删除组")
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
        List<Group> groups = new ArrayList<>();

        GroupCriteria groupCriteria = new GroupCriteria();

        if (!ObjectUtils.isEmpty(applicationId) && applicationId.longValue() != 0 && (ObjectUtils.isEmpty(tenantId) || tenantId.longValue() == 0)) {
            //应用级
            groupCriteria.createCriteria().andApplicationIdEqualTo(applicationId).andTenantIdEqualTo(0L).andStatusEqualTo(1);
            groups = this.selectByCriteria(groupCriteria);
        }

        if (!ObjectUtils.isEmpty(tenantId) && tenantId.longValue() != 0 && (ObjectUtils.isEmpty(applicationId) || applicationId.longValue() == 0)) {
            //租户级
            groupCriteria.createCriteria().andApplicationIdEqualTo(0L).andTenantIdEqualTo(tenantId).andStatusEqualTo(1);
            groups = this.selectByCriteria(groupCriteria);
        }

        if (!ObjectUtils.isEmpty(applicationId) && applicationId.longValue() != 0 && !ObjectUtils.isEmpty(tenantId) && tenantId.longValue() != 0) {
            //应用+租户
            GroupCriteria groupCriteria1 = new GroupCriteria();
            groupCriteria1.createCriteria().andApplicationIdEqualTo(applicationId).andTenantIdEqualTo(0L).andStatusEqualTo(1);
            GroupCriteria groupCriteria2 = new GroupCriteria();
            groupCriteria2.createCriteria().andApplicationIdEqualTo(0L).andTenantIdEqualTo(tenantId).andStatusEqualTo(1);
            GroupCriteria groupCriteria3 = new GroupCriteria();
            groupCriteria3.createCriteria().andApplicationIdEqualTo(applicationId).andTenantIdEqualTo(tenantId).andStatusEqualTo(1);

            List<Group> groupList1 = this.selectByCriteria(groupCriteria1);
            List<Group> groupList2 = this.selectByCriteria(groupCriteria2);
            List<Group> groupList3 = this.selectByCriteria(groupCriteria3);

            groups.addAll(groupList1);
            groups.addAll(groupList2);
            groups.addAll(groupList3);
        }

        //去重
        List<Group> groupList = new ArrayList<>();
        Set<Long> groupIdSet = new HashSet<>();
        for (Group group : groups) {
            if (!groupIdSet.contains(group.getId())) {
                groupIdSet.add(group.getId());
                groupList.add(group);
            }
        }

        return groupList;
    }

    @Override
    public List<Group> getGarnetGroupList(Long userId) {
        String level = resourceService.getLevelByUserIdAndPath(userId, GarnetContants.GARNET_DATA_USERMANAGE_GARNETGROUPLIST_PATH);
        List<Group> groupList = new ArrayList<>();
        if (Integer.valueOf(level) == 1) {
            //level=1，查询Garnet租户关联的组
            GroupCriteria groupCriteria = new GroupCriteria();
            groupCriteria.createCriteria().andTenantIdEqualTo(GarnetContants.GARNET_TENANT_ID).andStatusEqualTo(1);
            groupList = this.selectByCriteria(groupCriteria);
        } else if (Integer.valueOf(level) == 2) {
            GroupCriteria groupCriteria = new GroupCriteria();
            groupCriteria.createCriteria().andTenantIdEqualTo(GarnetContants.GARNET_TENANT_ID).andStatusEqualTo(1);
            List<Group> groupList1 = this.selectByCriteria(groupCriteria);
            for (Group group : groupList1) {
                String level1 = this.getLevelByGroupId(group.getId());
                System.out.println(group.getName() + " - " + level1);
                if (Integer.valueOf(level1) >= 2) {
                    groupList.add(group);
                }
            }
        }
        return groupList;
    }

    @LogRequired(module = "组管理模块", method = "查询组列表")
    @Override
    public PageUtil getGroupsByParams(GroupParm groupParm) {
        Long userId = groupParm.getUserId();

        GroupCriteria groupCriteria = new GroupCriteria();
        groupCriteria.setOrderByClause(GarnetContants.ORDER_BY_CREATED_TIME);
        GroupCriteria.Criteria criteria = groupCriteria.createCriteria();
        criteria.andStatusEqualTo(1);

        if (!ObjectUtils.isEmpty(groupParm.getSearchName())) {
            criteria.andNameLike("%" + groupParm.getSearchName() + "%");
        }

        if (!ObjectUtils.isEmpty(groupParm.getApplicationId())) {
            criteria.andApplicationIdEqualTo(groupParm.getApplicationId());
        }

        if (!ObjectUtils.isEmpty(groupParm.getTenantId())) {
            criteria.andTenantIdEqualTo(groupParm.getTenantId());
        }

        String level = resourceService.getLevelByUserIdAndPath(userId, GarnetContants.GARNET_DATA_GROUPMANAGE_QUERY_PATH);
        List<Group> groupList = new ArrayList<>();
        if (Integer.valueOf(level) == 1) {
            //全部数据
            groupList = this.selectByCriteria(groupCriteria);
        } else if (Integer.valueOf(level) == 2) {
            //非Garnet数据
            List<Long> tenantIdList = commonService.getTenantIdsNotGarnet(userId);
            criteria.andTenantIdIn(tenantIdList);
            List<Group> groupList1 = this.selectByCriteria(groupCriteria);

            //应用级数据，除去Garnet应用
            GroupCriteria groupCriteria1 = new GroupCriteria();
            groupCriteria1.createCriteria().andTenantIdEqualTo(0L).andApplicationIdNotEqualTo(GarnetContants.GARNET_APPLICATION_ID);
            List<Group> groupList2 = this.selectByCriteria(groupCriteria1);

            List<Group> groupList3 = new ArrayList<>();
            groupList3.addAll(groupList1);
            groupList3.addAll(groupList2);


            List<Long> groupIdList = new ArrayList<>();
            for (Group group : groupList3) {
                groupIdList.add(group.getId());
            }
            //去重
            Set<Long> groupIdSet = new HashSet<>();
            for (Group group : groupList3) {
                if (!groupIdSet.contains(group.getId())) {
                    groupIdSet.add(group.getId());
                    groupList.add(group);
                }
            }

//            UserTenantCriteria userTenantCriteria = new UserTenantCriteria();
//            userTenantCriteria.createCriteria().andTenantIdIn(tenantIdList);
//            List<UserTenant> userTenantList = userTenantService.selectByCriteria(userTenantCriteria);
//            List<Long> userIdList = new ArrayList<>();
//            for (UserTenant userTenant : userTenantList) {
//                userIdList.add(userTenant.getUserId());
//            }
//            if (userIdList.size() == 0) {
//                userIdList.add(GarnetContants.NON_VALUE);
//            }
//
//            GroupUserCriteria groupUserCriteria = new GroupUserCriteria();
//            groupUserCriteria.createCriteria().andUserIdIn(userIdList);
//            List<GroupUser> groupUserList = groupUserService.selectByCriteria(groupUserCriteria);
//            List<Long> groupIdList = new ArrayList<>();
//            for (GroupUser groupUser : groupUserList) {
//                groupIdList.add(groupUser.getGroupId());
//            }
//            if (groupIdList.size() == 0) {
//                groupIdList.add(GarnetContants.NON_VALUE);
//            }
//            criteria.andIdIn(groupIdList);
//            groupList = this.selectByCriteria(groupCriteria);
        } else if (Integer.valueOf(level) == 3) {
            //本用户为租户管理员的租户关联的组(不包括租户字段为空[即应用级]的组)
            List<Tenant> tenantList = tenantService.getTenantManageListByUserId(userId);
            List<Long> tenantIdList = new ArrayList<>();
            for (Tenant tenant : tenantList) {
                tenantIdList.add(tenant.getId());
            }

            criteria.andTenantIdIn(tenantIdList);
            groupList = this.selectByCriteria(groupCriteria);
        }

        GroupView groupView;
        List<GroupView> groupViewList = new ArrayList<>();
        for (Group group : groupList) {
            groupView = new GroupView();
            groupView.setGroup(group);
            if (group.getTenantId().longValue() != 0 && group.getApplicationId().longValue() == 0) {
                groupView.setType("租户");
            } else if (group.getTenantId().longValue() == 0 && group.getApplicationId().longValue() != 0) {
                groupView.setType("应用");
            } else {
                groupView.setType("租户+应用");
            }

            groupViewList.add(groupView);
        }

        PageUtil result = new PageUtil(groupViewList, groupViewList.size() ,groupParm.getPageSize(), groupParm.getPageNumber());
        return result;
    }

    /**
     * 通过组id获取用户权限（资源配置中的level）
     * @param groupId
     * @return
     */
    private String getLevelByGroupId(Long groupId) {
        GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
        groupRoleCriteria.createCriteria().andGroupIdEqualTo(groupId);
        List<GroupRole> groupRoleList = groupRoleService.selectByCriteria(groupRoleCriteria);
        List<Long> roleIdList = new ArrayList<>();
        for (GroupRole groupRole : groupRoleList) {
            roleIdList.add(groupRole.getRoleId());
        }

        //根据角色id拿permissions
        RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();
        rolePermissionCriteria.createCriteria().andRoleIdIn(roleIdList);
        List<RolePermission> rolePermissionList = rolePermissionService.selectByCriteria(rolePermissionCriteria);
        List<Long> permissionIdList = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissionList) {
            permissionIdList.add(rolePermission.getPermissionId());
        }

        PermissionCriteria permissionCriteria = new PermissionCriteria();
        permissionCriteria.createCriteria().andIdIn(permissionIdList).andResourcePathWildcardLike(GarnetContants.GARNET_DATA_USERMANAGE_GARNETGROUPLIST_PATH + "%");
        List<Permission> permissionList = permissionService.selectByCriteria(permissionCriteria);

        //根据权限id拿resources
        List<Resource> resourceList= new ArrayList<>();
        for (Permission permission : permissionList) {
            ResourceCriteria resourceCriteria = new ResourceCriteria();
            resourceCriteria.createCriteria().andPathLike(permission.getResourcePathWildcard());
            List<Resource> resourceList1 = resourceService.selectByCriteria(resourceCriteria);
            resourceList.addAll(resourceList1);
        }
        int level = 0;
        for (Resource resource : resourceList) {
            int level1 = Integer.valueOf(resource.getVarchar00());
            if (level == 0 || level1 < level) {
                //取值最小，level等级越高，取最高级
                level = level1;
            }
        }

        return String.valueOf(level);
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