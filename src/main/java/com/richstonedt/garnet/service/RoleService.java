package com.richstonedt.garnet.service;

import com.github.pagehelper.PageInfo;
import com.richstonedt.garnet.common.utils.PageUtil;
import com.richstonedt.garnet.model.Role;
import com.richstonedt.garnet.model.criteria.RoleCriteria;
import com.richstonedt.garnet.model.parm.RoleParm;
import com.richstonedt.garnet.model.view.RoleView;

public interface RoleService extends BaseService<Role, RoleCriteria, Long> {

    public Long insertRole(RoleView roleView);

    public void updateRole(RoleView roleView);

    public void deleteRole(RoleView roleView);

    public PageUtil queryRolesByParms(RoleParm roleParm);

}