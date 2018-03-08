package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.GroupUserMapper;
import com.richstonedt.garnet.model.GroupUser;
import com.richstonedt.garnet.model.criteria.GroupUserCriteria;
import com.richstonedt.garnet.service.GroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GroupUserServiceImpl extends BaseServiceImpl<GroupUser, GroupUserCriteria, Long> implements GroupUserService {
    @Autowired
    private GroupUserMapper groupUserMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return this.groupUserMapper;
    }
}