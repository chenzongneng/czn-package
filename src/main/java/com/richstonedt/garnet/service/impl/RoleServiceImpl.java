package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleCriteria, Long> implements RoleService {

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

    @Override
    public BaseMapper getBaseMapper() {
        return this.roleMapper;
    }

    @Override
    public Long insertRole(RoleView roleView) {

        Role role = roleView.getRole();
        role.setId(IdGeneratorUtil.generateId());
        Long currentTime = new Date().getTime();
        role.setCreatedTime(currentTime);
        role.setModifiedTime(currentTime);
        this.insertSelective(role);

        if(!ObjectUtils.isEmpty(roleView.getGroupIds())){
            for (Long groupId: roleView.getGroupIds()) {
                GroupRole groupRole = new GroupRole();
                groupRole.setId(IdGeneratorUtil.generateId());
                groupRole.setGroupId(groupId);
                groupRole.setRoleId(role.getId());
                groupRoleService.insertSelective(groupRole);
            }
        }

        if(!ObjectUtils.isEmpty(roleView.getPermissionIds())){
            for (Long permissionId : roleView.getPermissionIds()) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setId(IdGeneratorUtil.generateId());
                rolePermission.setPermissionId(permissionId);
                rolePermission.setRoleId(role.getId());
                rolePermissionService.insertSelective(rolePermission);
            }
        }
        return role.getId();
    }

    @Override
    public void updateRole(RoleView roleView) {

        if (ObjectUtils.isEmpty(roleView.getRole()) || ObjectUtils.isEmpty(roleView.getRole().getId())) {
            throw new RuntimeException("role or role's id can not be null");
        }

        Role role = roleView.getRole();
        Long currentTime = new Date().getTime();
        role.setModifiedTime(currentTime);
        this.updateByPrimaryKeySelective(role);

        if(!ObjectUtils.isEmpty(roleView.getGroupIds())){
            GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
            groupRoleCriteria.createCriteria().andRoleIdEqualTo(role.getId());
            //删除关联的组
            groupRoleService.deleteByCriteria(groupRoleCriteria);
            for (Long groupId : roleView.getGroupIds()) {
                GroupRole groupRole = new GroupRole();
                groupRole.setGroupId(groupId);
                groupRole.setRoleId(role.getId());
                groupRole.setId(IdGeneratorUtil.generateId());
                groupRoleService.insertSelective(groupRole);
            }
        }

        if(!ObjectUtils.isEmpty(roleView.getPermissionIds())){
            RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();
            rolePermissionCriteria.createCriteria().andRoleIdEqualTo(role.getId());
            //删除关联的权限
            rolePermissionService.deleteByCriteria(rolePermissionCriteria);
            for (Long permissionId : roleView.getPermissionIds()) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(role.getId());
                rolePermission.setPermissionId(permissionId);
                rolePermission.setId(IdGeneratorUtil.generateId());
                rolePermissionService.insertSelective(rolePermission);
            }
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
        RoleCriteria.Criteria criteria = roleCriteria.createCriteria();
        //只查询status为1，即没被删除的
        criteria.andStatusEqualTo(1);

        if (!StringUtils.isEmpty(roleParm.getUserId())) {
            ReturnTenantIdView returnTenantIdView = userService.getTenantIdsByUserId(roleParm.getUserId());
            List<Long> tenantIds = returnTenantIdView.getTenantIds();

            //如果不是超级管理员,根据tenantId返回列表
            if (!returnTenantIdView.isSuperAdmin()) {
                //change by ming
                tenantIds =  commonService.dealTenantIdsIfGarnet(roleParm.getUserId(),tenantIds);
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

        PageUtil result = new PageUtil(roleViews, (int)this.countByCriteria(roleCriteria),roleParm.getPageNumber() ,roleParm.getPageSize());
        return result;
    }

    @Override
    public void updateStatusById(Role role) {
        //先删除关联外键
        GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
        groupRoleCriteria.createCriteria().andRoleIdEqualTo(role.getId());
        groupRoleService.deleteByCriteria(groupRoleCriteria);

        RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();
        rolePermissionCriteria.createCriteria().andRoleIdEqualTo(role.getId());
        rolePermissionService.deleteByCriteria(rolePermissionCriteria);

        Long currentTime = new Date().getTime();
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
        GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
        groupRoleCriteria.createCriteria().andRoleIdEqualTo(role.getId());
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
            roleView.setGroupNames(groupNames);
        }

        //设置权限名称列表
        RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();
        rolePermissionCriteria.createCriteria().andRoleIdEqualTo(role.getId());
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
            roleView.setPermissionNames(permissionNames);
        }

        return roleView;
    }

}