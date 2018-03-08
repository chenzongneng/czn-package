package com.richstonedt.garnet.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.richstonedt.garnet.common.utils.IdGeneratorUtil;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.RoleMapper;
import com.richstonedt.garnet.model.Group;
import com.richstonedt.garnet.model.GroupRole;
import com.richstonedt.garnet.model.Role;
import com.richstonedt.garnet.model.RolePermission;
import com.richstonedt.garnet.model.criteria.GroupRoleCriteria;
import com.richstonedt.garnet.model.criteria.RoleCriteria;
import com.richstonedt.garnet.model.criteria.RolePermissionCriteria;
import com.richstonedt.garnet.model.parm.RoleParm;
import com.richstonedt.garnet.model.view.RoleView;
import com.richstonedt.garnet.service.GroupRoleService;
import com.richstonedt.garnet.service.RolePermissionService;
import com.richstonedt.garnet.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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

        if(!ObjectUtils.isEmpty(roleView.getGroupRoles())){

            for (GroupRole groupRole:
                    roleView.getGroupRoles()) {

                groupRole.setRoleId(role.getId());
                groupRole.setId(IdGeneratorUtil.generateId());
                groupRoleService.insertSelective(groupRole);

            }
        }

        if(!ObjectUtils.isEmpty(roleView.getRolePermissions())){

            for (RolePermission rolePermission:
                    roleView.getRolePermissions()) {

                rolePermission.setRoleId(role.getId());
                rolePermission.setId(IdGeneratorUtil.generateId());
                rolePermissionService.insertSelective(rolePermission);

            }
        }

        return role.getId();

    }

    @Override
    public void updateRole(RoleView roleView) {

        Role role = roleView.getRole();

        Long currentTime = new Date().getTime();

        role.setModifiedTime(currentTime);

        this.updateByPrimaryKeySelective(role);

        if(!ObjectUtils.isEmpty(roleView.getGroupRoles())){

            GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();

            groupRoleCriteria.createCriteria().andGroupIdEqualTo(role.getId());

            groupRoleService.deleteByCriteria(groupRoleCriteria);

            for (GroupRole groupRole:
                    roleView.getGroupRoles()) {

                groupRole.setRoleId(role.getId());
                groupRole.setId(IdGeneratorUtil.generateId());
                groupRoleService.insertSelective(groupRole);

            }
        }

        if(!ObjectUtils.isEmpty(roleView.getRolePermissions())){

            RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();

            rolePermissionCriteria.createCriteria().andRoleIdEqualTo(role.getId());

            rolePermissionService.deleteByCriteria(rolePermissionCriteria);

            for (RolePermission rolePermission:
                    roleView.getRolePermissions()) {

                rolePermission.setRoleId(role.getId());
                rolePermission.setId(IdGeneratorUtil.generateId());
                rolePermissionService.insertSelective(rolePermission);

            }
        }

    }

    @Override
    public void deleteRole(RoleView roleView) {

        Role role = roleView.getRole();

        this.deleteByPrimaryKey(role.getId());

        if(!ObjectUtils.isEmpty(roleView.getGroupRoles())){

            GroupRoleCriteria groupRoleCriteria = new GroupRoleCriteria();

            groupRoleCriteria.createCriteria().andGroupIdEqualTo(role.getId());

            groupRoleService.deleteByCriteria(groupRoleCriteria);

        }

        if(!ObjectUtils.isEmpty(roleView.getRolePermissions())){

            RolePermissionCriteria rolePermissionCriteria = new RolePermissionCriteria();

            rolePermissionCriteria.createCriteria().andRoleIdEqualTo(role.getId());

            rolePermissionService.deleteByCriteria(rolePermissionCriteria);

        }

    }

    @Override
    public PageUtil queryRolesByParms(RoleParm roleParm) {

        Role role = roleParm.getRole();

        RoleCriteria roleCriteria = new RoleCriteria();

        if(!ObjectUtils.isEmpty(roleParm.getApplicationId())){

            roleCriteria.createCriteria().andApplicationIdEqualTo(roleParm.getApplicationId());

        }
        if(!ObjectUtils.isEmpty(roleParm.getTenantId())){

            roleCriteria.createCriteria().andTenantIdEqualTo(roleParm.getTenantId());
        }

        PageUtil result = new PageUtil(this.selectByCriteria(roleCriteria), (int)this.countByCriteria(roleCriteria),roleParm.getPageNumber() ,roleParm.getPageSize());

        return result;
    }
}