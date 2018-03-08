package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.UserTenantMapper;
import com.richstonedt.garnet.model.UserTenant;
import com.richstonedt.garnet.model.criteria.UserTenantCriteria;
import com.richstonedt.garnet.service.UserTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserTenantServiceImpl extends BaseServiceImpl<UserTenant, UserTenantCriteria, Long> implements UserTenantService {
    @Autowired
    private UserTenantMapper userTenantMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return this.userTenantMapper;
    }
}