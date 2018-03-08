package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.ResourceDynamicPropertyMapper;
import com.richstonedt.garnet.model.ResourceDynamicProperty;
import com.richstonedt.garnet.model.criteria.ResourceDynamicPropertyCriteria;
import com.richstonedt.garnet.service.ResourceDynamicPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResourceDynamicPropertyServiceImpl extends BaseServiceImpl<ResourceDynamicProperty, ResourceDynamicPropertyCriteria, Long> implements ResourceDynamicPropertyService {
    @Autowired
    private ResourceDynamicPropertyMapper resourceDynamicPropertyMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return this.resourceDynamicPropertyMapper;
    }
}