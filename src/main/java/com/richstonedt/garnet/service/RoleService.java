package com.richstonedt.garnet.service;

import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Role;
import com.richstonedt.garnet.model.criteria.RoleCriteria;
import com.richstonedt.garnet.model.parm.RoleParm;
import com.richstonedt.garnet.model.view.RoleView;

import java.util.List;

public interface RoleService extends BaseService<Role, RoleCriteria, Long> {

    public Long insertRole(RoleView roleView);

    public void updateRole(RoleView roleView);

    public void deleteRole(RoleView roleView);

    public PageUtil queryRolesByParms(RoleParm roleParm);

    /**
     * 当用户点击删除时，将其状态设为禁用
     * @param role
     */
    public void updateStatusById(Role role);

    public RoleView selectRoleWithGroupAndPermission(Long id);

    public List<Role> queryRolesByTenantId(RoleParm roleParm);

    List<Role> queryRoles();

}