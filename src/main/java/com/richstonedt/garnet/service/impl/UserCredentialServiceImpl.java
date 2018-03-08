package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.UserCredentialMapper;
import com.richstonedt.garnet.model.UserCredential;
import com.richstonedt.garnet.model.criteria.UserCredentialCriteria;
import com.richstonedt.garnet.service.UserCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserCredentialServiceImpl extends BaseServiceImpl<UserCredential, UserCredentialCriteria, Long> implements UserCredentialService {
    @Autowired
    private UserCredentialMapper userCredentialMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return this.userCredentialMapper;
    }
}