package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.UserTenantApplicationMapper;
import com.richstonedt.garnet.model.UserTenantApplication;
import com.richstonedt.garnet.model.criteria.UserTenantApplicationCriteria;
import com.richstonedt.garnet.service.UserTenantApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserTenantApplicationServiceImpl extends BaseServiceImpl<UserTenantApplication, UserTenantApplicationCriteria, Long> implements UserTenantApplicationService {
    @Autowired
    private UserTenantApplicationMapper userTenantApplicationMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return this.userTenantApplicationMapper;
    }
}