package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.Token;
import com.richstonedt.garnet.model.criteria.TokenCriteria;

public interface TokenService extends BaseService<Token, TokenCriteria, Long> {

    /**
     * roueterGroupName查找唯一token
     * @param roueterGroupName
     * @return
     */
    String getTokenByRouterGroupName(String roueterGroupName);


}