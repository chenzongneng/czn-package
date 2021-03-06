package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.contants.GarnetContants;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.interceptory.LogRequired;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.RoleMapper;
import com.richstonedt.garnet.model.*;
import com.richstonedt.garnet.model.criteria.GroupRoleCriteria;
import com.richstonedt.garnet.model.criteria.RoleCriteria;
import com.richstonedt.garnet.model.criteria.RolePermissionCriteria;
import com.richstonedt.garnet.model.criteria.UserTenantCriteria;
import com.richstonedt.garnet.model.parm.RoleParm;
import com.richstonedt.garnet.model.view.ReturnTenantIdView;
import com.richstonedt.garnet.model.view.RoleView;
import com.richstonedt.garnet.service.*;
//import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleCriteria, Long> implements RoleService {

    private static final Long TENANTID_NULL = 0L;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private GroupRoleService groupRoleService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserTenantService userTenantService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ResourceService resourceService;

    @Override
    public BaseMapper getBaseMapper() {
        return this.roleMapper;
    }

    @LogRequired(module = "角色管理模块", method = "新增角色")
    @Override
    public Long insertRole(RoleView roleView) {

        Role role = roleView.getRole();
        role.setId(IdGeneratorUtil.generateId());
        Long currentTime = System.currentTimeMillis();
        role.setCreatedTime(currentTime);
        role.setModifiedTime(currentTime);

        //检查角色名称是否已被使用
        checkDuplicateName(role);

        this.insertSelective(role);

        if(!ObjectUtils.isEmpty(roleView.getGroupIds())){
            for (Long groupId: roleView.getGroupIds()) {
                GroupRole groupRole = new GroupRole();
                groupRole.setId(IdGeneratorUtil.generateId());
                groupRole.setGroupId(groupId);
                groupRole.setRoleId(role.getId());
                groupRoleService.insertSelective(groupRole);
            }

            Log log = new Log();
            log.setMessage("角色管理模块");
            log.setOperation("角色绑定组");
            commonService.insertLog(log);
        }

        if(!ObjectUtils.isEmpty(roleView.getPermissionIds())){
            for (Long permissionId : roleView.getPermissionIds()) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setId(IdGeneratorUtil.generateId());
                rolePermission.setPermissionId(permissionId);
                rolePermission.setRoleId(role.getId());
                rolePermissionService.insertSelective(rolePermission);
            }

            Log log = new Log();
            log.setMessage("角色管理模块");
            log.setOperation("角色绑定权限");
            commonService.insertLog(log);
        }
        return role.getId();
    }

    @LogRequired(module = "角色管理模块", method = "配置角色")
    @Override
    public void updateRole(RoleView roleView) {

        if (ObjectUtils.isEmpty(roleView.getRole()) || ObjectUtils.isEmpty(roleView.getRole().getId())) {
            throw new RuntimeException("role or role's id can not be null");
        }

        Role role = roleView.getRole();
        Long currentTime = System.currentTimeMillis();
        role.setModifiedTime(currentTime);

        //检查角色名称是否已经存在
        checkDuplicateName(role);

        this.updateByPrimaryKeySelective(role);

        //删除关联的组
        GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
        groupRoleCriteria.createCriteria().andRoleIdEqualTo(role.getId());
        groupRoleService.deleteByCriteria(groupRoleCriteria);

        //重新添加关联组
        if(!ObjectUtils.isEmpty(roleView.getGroupIds())){
            for (Long groupId : roleView.getGroupIds()) {
                GroupRole groupRole = new GroupRole();
                groupRole.setGroupId(groupId);
                groupRole.setRoleId(role.getId());
                groupRole.setId(IdGeneratorUtil.generateId());
                groupRoleService.insertSelective(groupRole);
            }

            Log log = new Log();
            log.setMessage("角色管理模块");
            log.setOperation("角色绑定组");
            commonService.insertLog(log);
        }

        //删除关联的权限
        RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();
        rolePermissionCriteria.createCriteria().andRoleIdEqualTo(role.getId());
        rolePermissionService.deleteByCriteria(rolePermissionCriteria);

        //重新添加关联权限
        if(!ObjectUtils.isEmpty(roleView.getPermissionIds())){
            RolePermission rolePermission;
            Long rolePermissionId = IdGeneratorUtil.generateId();
            for (Long permissionId : roleView.getPermissionIds()) {
                System.out.println("rolePermissionId ========================" + rolePermissionId);
                rolePermission = new RolePermission();
                rolePermission.setRoleId(role.getId());
                rolePermission.setPermissionId(permissionId);
                rolePermission.setId(rolePermissionId);
                rolePermissionService.insertSelective(rolePermission);
                rolePermissionId = rolePermissionId + 1L;
            }

            Log log = new Log();
            log.setMessage("角色管理模块");
            log.setOperation("角色绑定权限");
            commonService.insertLog(log);
        }

    }

    @Override
    public void deleteRole(RoleView roleView) {

        if (ObjectUtils.isEmpty(roleView.getRole()) || ObjectUtils.isEmpty(roleView.getRole().getId())) {
            throw new RuntimeException("role or role's id can not be null");
        }
        Role role = roleView.getRole();
        this.deleteByPrimaryKey(role.getId());

        //删除关联组
        GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
        groupRoleCriteria.createCriteria().andRoleIdEqualTo(role.getId());
        groupRoleService.deleteByCriteria(groupRoleCriteria);

        //删除关联权限
        RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();
        rolePermissionCriteria.createCriteria().andRoleIdEqualTo(role.getId());
        rolePermissionService.deleteByCriteria(rolePermissionCriteria);

//        if(!ObjectUtils.isEmpty(roleView.getGroupRoles())){
//            GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
//            groupRoleCriteria.createCriteria().andGroupIdEqualTo(role.getId());
//            groupRoleService.deleteByCriteria(groupRoleCriteria);
//        }

//        if(!ObjectUtils.isEmpty(roleView.getRolePermissions())){
//            RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();
//            rolePermissionCriteria.createCriteria().andRoleIdEqualTo(role.getId());
//            rolePermissionService.deleteByCriteria(rolePermissionCriteria);
//        }

    }

    @Override
    public PageUtil queryRolesByParms(RoleParm roleParm) {

        Role role = roleParm.getRole();
        RoleCriteria roleCriteria = new RoleCriteria();
        roleCriteria.setOrderByClause(GarnetContants.ORDER_BY_CREATED_TIME);
        RoleCriteria.Criteria criteria = roleCriteria.createCriteria();
        //只查询status为1，即没被删除的
        criteria.andStatusEqualTo(1);

        if (!StringUtils.isEmpty(roleParm.getSearchName())) {
            criteria.andNameLike("%" + roleParm.getSearchName() + "%");
        }

        if (!ObjectUtils.isEmpty(roleParm.getApplicationId())) {
            criteria.andApplicationIdEqualTo(roleParm.getApplicationId());
        }

        if (!ObjectUtils.isEmpty(roleParm.getTenantId())) {
            criteria.andTenantIdEqualTo(roleParm.getTenantId());
        }

        if (!StringUtils.isEmpty(roleParm.getUserId())) {
            ReturnTenantIdView returnTenantIdView = userService.getTenantIdsByUserId(roleParm.getUserId());
            List<Long> tenantIds = returnTenantIdView.getTenantIds();

            //如果不是超级管理员,根据tenantId返回列表
            if (!returnTenantIdView.isSuperAdmin() || (returnTenantIdView.isSuperAdmin() && !commonService.superAdminBelongGarnet(roleParm.getUserId()))) {
                if (!CollectionUtils.isEmpty(tenantIds) && tenantIds.size() > 0) {
                    //根据tenantId列表查询role
                    criteria.andTenantIdIn(tenantIds);
                } else {
                    return  new PageUtil(null, 0,roleParm.getPageNumber() ,roleParm.getPageSize());
                }
            }
        }

        List<Role> roles = this.selectByCriteria(roleCriteria);
        List<RoleView> roleViews = new ArrayList<>();
        for (Role role1 : roles) {
            roleViews.add(convertToRoleView(role1));
        }

        PageUtil result = new PageUtil(roleViews, (int)this.countByCriteria(roleCriteria), roleParm.getPageSize(),roleParm.getPageNumber());
        return result;
    }

    @LogRequired(module = "角色管理模块", method = "删除角色")
    @Override
    public void updateStatusById(Role role) {

        if (GarnetContants.GARNET_ROLE_ID.longValue() == role.getId().longValue()) {
            throw new RuntimeException("不能删除超级角色");
        }

        //先删除关联外键
        GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
        groupRoleCriteria.createCriteria().andRoleIdEqualTo(role.getId());
        groupRoleService.deleteByCriteria(groupRoleCriteria);

        RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();
        rolePermissionCriteria.createCriteria().andRoleIdEqualTo(role.getId());
        rolePermissionService.deleteByCriteria(rolePermissionCriteria);

        Long currentTime = System.currentTimeMillis();
        role.setModifiedTime(currentTime);
        role.setStatus(0);
        this.updateByPrimaryKeySelective(role);
    }

    @Override
    public RoleView selectRoleWithGroupAndPermission(Long id) {
        RoleView roleView = new RoleView();
        Role role = this.selectByPrimaryKey(id);
        roleView.setRole(role);

        //查关联的groups
        GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
        groupRoleCriteria.createCriteria().andRoleIdEqualTo(id);

        List<GroupRole> groupRoles = groupRoleService.selectByCriteria(groupRoleCriteria);
        List<Long> groupIds = new ArrayList<>();
        for (GroupRole groupRole : groupRoles) {
            if (!ObjectUtils.isEmpty(groupRole.getGroupId())) {
                groupIds.add(groupRole.getGroupId());
            }
        }
        roleView.setGroupIds(groupIds);

        //查关联的permissions
        RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();
        rolePermissionCriteria.createCriteria().andRoleIdEqualTo(id);
        List<RolePermission> rolePermissions = rolePermissionService.selectByCriteria(rolePermissionCriteria);
        List<Long> permissionIds = new ArrayList<>();
        for (RolePermission rolePermission : rolePermissions) {
            if (!ObjectUtils.isEmpty(rolePermission.getPermissionId())) {
                permissionIds.add(rolePermission.getPermissionId());
            }
        }
        roleView.setPermissionIds(permissionIds);
        return roleView;

    }

    @Override
    public List<Role> queryRolesByTenantId(RoleParm roleParm) {
        Long tenantId = roleParm.getTenantId();

        RoleCriteria roleCriteria = new RoleCriteria();
        roleCriteria.createCriteria().andTenantIdEqualTo(tenantId).andStatusEqualTo(1);
        List<Role> roles = this.selectByCriteria(roleCriteria);

        return roles;
    }

    @Override
    public List<Role> queryRoles() {
        RoleCriteria roleCriteria = new RoleCriteria();
        roleCriteria.createCriteria().andStatusEqualTo(1);
        List<Role> roles = this.selectByCriteria(roleCriteria);
        return roles;
    }

    @Override
    public List<Role> queryRolesByApplicationId(RoleParm roleParm) {
        Long applicationId = roleParm.getApplicationId();

        RoleCriteria roleCriteria = new RoleCriteria();
        roleCriteria.createCriteria().andStatusEqualTo(1).andApplicationIdEqualTo(applicationId);
        List<Role> roles = this.selectByCriteria(roleCriteria);

        return roles;
    }

    @Override
    public long countByCriteria(RoleCriteria criteria) {
        return super.countByCriteria(criteria);
    }

    @Override
    public List<Role> queryRolesByParams(RoleParm roleParm) {
        Long applicationId = roleParm.getApplicationId();
        Long tenantId = roleParm.getTenantId();

        List<Role> roles = new ArrayList<>();
        RoleCriteria roleCriteria = new RoleCriteria();

        if (!ObjectUtils.isEmpty(applicationId) && applicationId.longValue() != 0 && (ObjectUtils.isEmpty(tenantId) || tenantId.longValue() == 0)) {
            //应用级
            roleCriteria.createCriteria().andApplicationIdEqualTo(applicationId).andTenantIdEqualTo(0L).andStatusEqualTo(1);
            roles = this.selectByCriteria(roleCriteria);
        }

        if (!ObjectUtils.isEmpty(tenantId) && tenantId.longValue() != 0 && (ObjectUtils.isEmpty(applicationId) || applicationId.longValue() == 0)) {
            //租户级
            roleCriteria.createCriteria().andApplicationIdEqualTo(0L).andTenantIdEqualTo(tenantId).andStatusEqualTo(1);
            roles = this.selectByCriteria(roleCriteria);
        }

        if (!ObjectUtils.isEmpty(tenantId) && tenantId.longValue() != 0 && !ObjectUtils.isEmpty(applicationId) && applicationId.longValue() != 0) {
            //应用+租户
            RoleCriteria roleCriteria1 = new RoleCriteria();
            roleCriteria1.createCriteria().andApplicationIdEqualTo(applicationId).andTenantIdEqualTo(0L).andStatusEqualTo(1);
            List<Role> roleList1 = this.selectByCriteria(roleCriteria1);
            RoleCriteria roleCriteria2 = new RoleCriteria();
            roleCriteria2.createCriteria().andApplicationIdEqualTo(0L).andTenantIdEqualTo(tenantId).andStatusEqualTo(1);
            List<Role> roleList2 = this.selectByCriteria(roleCriteria2);
            RoleCriteria roleCriteria3 = new RoleCriteria();
            roleCriteria3.createCriteria().andApplicationIdEqualTo(applicationId).andTenantIdEqualTo(tenantId).andStatusEqualTo(1);
            List<Role> roleList3 = this.selectByCriteria(roleCriteria3);

            roles.addAll(roleList1);
            roles.addAll(roleList2);
            roles.addAll(roleList3);
        }

        //去重
        Set<Long> roleIdSet = new HashSet<>();
        List<Role> roleList = new ArrayList<>();
        for (Role role : roles) {
            if (!roleIdSet.contains(role.getId())) {
                roleIdSet.add(role.getId());
                roleList.add(role);
            }
        }

        return roleList;
    }

    @LogRequired(module = "角色管理模块", method = "查询角色列表")
    @Override
    public PageUtil getRolesByParams(RoleParm roleParm) {
        Long userId = roleParm.getUserId();

        RoleCriteria roleCriteria = new RoleCriteria();
        roleCriteria.setOrderByClause(GarnetContants.ORDER_BY_CREATED_TIME);
        RoleCriteria.Criteria criteria = roleCriteria.createCriteria();
        criteria.andStatusEqualTo(1);

        if (!StringUtils.isEmpty(roleParm.getSearchName())) {
            criteria.andNameLike("%" + roleParm.getSearchName() + "%");
        }

        if (!ObjectUtils.isEmpty(roleParm.getApplicationId())) {
            criteria.andApplicationIdEqualTo(roleParm.getApplicationId());
        }

        if (!ObjectUtils.isEmpty(roleParm.getTenantId())) {
            criteria.andTenantIdEqualTo(roleParm.getTenantId());
        }

        String level = resourceService.getLevelByUserIdAndPath(userId, GarnetContants.GARNET_DATA_ROLEMANAGE_QUERY_PATH);
        List<Role> roleList = new ArrayList<>();
        if (Integer.valueOf(level) == 1) {
            roleList = this.selectByCriteria(roleCriteria);
        } else if (Integer.valueOf(level) == 2) {
            //非Garnet数据
            List<Long> tenantIdList = commonService.getTenantIdsNotGarnet(userId);
            RoleCriteria roleCriteria1 = new RoleCriteria();
            roleCriteria1.createCriteria().andTenantIdIn(tenantIdList).andStatusEqualTo(1);
            List<Role> roleList1 = this.selectByCriteria(roleCriteria1);

            RoleCriteria roleCriteria2 = new RoleCriteria();
            roleCriteria2.createCriteria().andTenantIdEqualTo(TENANTID_NULL).andApplicationIdNotEqualTo(GarnetContants.GARNET_APPLICATION_ID).andStatusEqualTo(1);
            List<Role> roleList2 = this.selectByCriteria(roleCriteria2);

            List<Role> roleList3 = new ArrayList<>();
            roleList3.addAll(roleList1);
            roleList3.addAll(roleList2);

            Set<Long> roleIdSet = new HashSet<>();
            for (Role role : roleList3) {
                if (!roleIdSet.contains(role.getId())) {
                    roleList.add(role);
                    roleIdSet.add(role.getId());
                }
            }

//            List<Long> roleIdList = new ArrayList<>();
//            for (Role role : roleList3) {
//                roleIdList.add(role.getId());
//            }
//            if (roleIdList.size() == 0) {
//                roleIdList.add(GarnetContants.NON_VALUE);
//            }
//
//            criteria.andIdIn(roleIdList);
//            roleList = this.selectByCriteria(roleCriteria);
        } else if (Integer.valueOf(level) == 3) {
            //本用户为租户管理员的租户关联的角色(不包括租户字段为空[即应用级]的角色)
            List<Tenant> tenantList = tenantService.getTenantManageListByUserId(userId);
            List<Long> tenantIdList = new ArrayList<>();
            for (Tenant tenant : tenantList) {
                tenantIdList.add(tenant.getId());
            }
            if (tenantIdList.size() == 0) {
                tenantIdList.add(GarnetContants.NON_VALUE);
            }

            criteria.andTenantIdIn(tenantIdList);
            roleList = this.selectByCriteria(roleCriteria);
        }

        List<RoleView> roleViews = new ArrayList<>();
        for (Role role1 : roleList) {
            roleViews.add(convertToRoleView(role1));
        }
        PageUtil result = new PageUtil(roleViews, roleViews.size(), roleParm.getPageSize(),roleParm.getPageNumber());
        return result;
    }

    private RoleView convertToRoleView(Role role) {
        RoleView roleView = new RoleView();
        roleView.setRole(role);

        //设置租户名称
        if (!ObjectUtils.isEmpty(role.getTenantId())) {
            Tenant tenant = tenantService.selectByPrimaryKey(role.getTenantId());
            if (!ObjectUtils.isEmpty(tenant) && !StringUtils.isEmpty(tenant.getName())) {
                roleView.setTenantName(tenant.getName());
            }
        }

        //设置应用名称
        if (!ObjectUtils.isEmpty(role.getApplicationId())) {
            Application application = applicationService.selectByPrimaryKey(role.getApplicationId());
            if (!ObjectUtils.isEmpty(application) && !StringUtils.isEmpty(application.getName())) {
                roleView.setApplicationName(application.getName());
            }
        }

        //设置部门名称列表
        List<String> groupName = this.getGroupNamesByRoleId(role.getId());
        roleView.setGroupNames(groupName);

        //设置权限名称列表
        List<String> perminssionNames = this.getPermissionNamesByRoleId(role.getId());
        roleView.setPermissionNames(perminssionNames);

        if (role.getTenantId().longValue() != 0 && role.getApplicationId().longValue() == 0) {
            roleView.setType("租户");
        } else if (role.getTenantId().longValue() == 0 && role.getApplicationId().longValue() != 0) {
            roleView.setType("应用");
        } else {
            roleView.setType("租户+应用");
        }

        return roleView;
    }

    private List<String> getPermissionNamesByRoleId(Long roleId) {
        RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();
        rolePermissionCriteria.createCriteria().andRoleIdEqualTo(roleId);
        List<RolePermission> rolePermissions = rolePermissionService.selectByCriteria(rolePermissionCriteria);
        List<String> permissionNames = new ArrayList<>();
        if (!CollectionUtils.isEmpty(rolePermissions)) {
            for (RolePermission rolePermission : rolePermissions) {
                Long permissionId = rolePermission.getPermissionId();
                Permission permission = permissionService.selectByPrimaryKey(permissionId);
                if (!ObjectUtils.isEmpty(permission) && !StringUtils.isEmpty(permission.getName())) {
                    permissionNames.add(permission.getName());
                }
            }
            return permissionNames;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 获取角色绑定的所有 组名称
     * @param roleId
     * @return
     */
    private List<String> getGroupNamesByRoleId(Long roleId) {
        GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
        groupRoleCriteria.createCriteria().andRoleIdEqualTo(roleId);
        List<GroupRole> groupRoles = groupRoleService.selectByCriteria(groupRoleCriteria);
        List<String> groupNames = new ArrayList<>();
        if (!CollectionUtils.isEmpty(groupRoles)) {
            for (GroupRole groupRole : groupRoles) {
                Long groupId = groupRole.getGroupId();
                Group group = groupService.selectByPrimaryKey(groupId);
                if (!ObjectUtils.isEmpty(group) && !StringUtils.isEmpty(group.getName())) {
                    groupNames.add(group.getName());
                }
            }
            return groupNames;
        } else {
            return new ArrayList<>();
        }

    }

    private void  checkDuplicateName(Role role) {

        String name = role.getName();
        Long id = role.getId();

        RoleCriteria roleCriteria = new RoleCriteria();
        roleCriteria.createCriteria().andNameEqualTo(name).andStatusEqualTo(1);
        Role role1 = this.selectSingleByCriteria(roleCriteria);
        if (!ObjectUtils.isEmpty(role1) && role1.getId().longValue() != id.longValue()) {
            throw new RuntimeException("角色名称已被使用");
        }

    }
}