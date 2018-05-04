package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.UserCredential;
import com.richstonedt.garnet.model.criteria.UserCredentialCriteria;

public interface UserCredentialService extends BaseService<UserCredential, UserCredentialCriteria, Long> {

    /**
     * 根据用户名查询密码等信息
     * @param userName
     * @return
     */
    UserCredential getCredentialByUserName(String userName);
}