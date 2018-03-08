package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.GroupRoleMapper;
import com.richstonedt.garnet.model.GroupRole;
import com.richstonedt.garnet.model.criteria.GroupRoleCriteria;
import com.richstonedt.garnet.service.GroupRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupRoleServiceImpl extends BaseServiceImpl<GroupRole, GroupRoleCriteria, Long> implements GroupRoleService {
    @Autowired
    private GroupRoleMapper groupRoleMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return this.groupRoleMapper;
    }
}