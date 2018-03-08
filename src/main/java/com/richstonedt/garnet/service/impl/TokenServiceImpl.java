package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.TokenMapper;
import com.richstonedt.garnet.model.Token;
import com.richstonedt.garnet.model.criteria.TokenCriteria;
import com.richstonedt.garnet.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TokenServiceImpl extends BaseServiceImpl<Token, TokenCriteria, Long> implements TokenService {
    @Autowired
    private TokenMapper tokenMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return this.tokenMapper;
    }
}