package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.mapper.ApplicationTenantMapper;
import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.model.ApplicationTenant;
import com.richstonedt.garnet.model.criteria.ApplicationTenantCriteria;
import com.richstonedt.garnet.service.ApplicationTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ApplicationTenantServiceImpl extends BaseServiceImpl<ApplicationTenant, ApplicationTenantCriteria, Long> implements ApplicationTenantService {

    @Autowired
    private ApplicationTenantMapper applicationTenantMapper;
    //private ApplicationTenantMapper applicationTenantMapper;


    @Override
    public BaseMapper getBaseMapper() {
        return this.applicationTenantMapper;
    }
}