package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.RoleMapper;
import com.richstonedt.garnet.model.GroupRole;
import com.richstonedt.garnet.model.Role;
import com.richstonedt.garnet.model.RolePermission;
import com.richstonedt.garnet.model.criteria.GroupCriteria;
import com.richstonedt.garnet.model.criteria.GroupRoleCriteria;
import com.richstonedt.garnet.model.criteria.RoleCriteria;
import com.richstonedt.garnet.model.criteria.RolePermissionCriteria;
import com.richstonedt.garnet.model.parm.RoleParm;
import com.richstonedt.garnet.model.view.RoleView;
import com.richstonedt.garnet.service.GroupRoleService;
import com.richstonedt.garnet.service.RolePermissionService;
import com.richstonedt.garnet.service.RoleService;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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
            groupRoleCriteria.createCriteria().andGroupIdEqualTo(role.getId());
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

        GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();
        groupRoleCriteria.createCriteria().andRoleIdEqualTo(role.getId());
        groupRoleService.deleteByCriteria(groupRoleCriteria);

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

        //只查询status为1，即没被删除的
        roleCriteria.createCriteria().andStatusEqualTo(1);

        if(!ObjectUtils.isEmpty(roleParm.getApplicationId())){
            roleCriteria.createCriteria().andApplicationIdEqualTo(roleParm.getApplicationId());
        }
        if(!ObjectUtils.isEmpty(roleParm.getTenantId())){
            roleCriteria.createCriteria().andTenantIdEqualTo(roleParm.getTenantId());
        }

        PageUtil result = new PageUtil(this.selectByCriteria(roleCriteria), (int)this.countByCriteria(roleCriteria),roleParm.getPageNumber() ,roleParm.getPageSize());

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
}