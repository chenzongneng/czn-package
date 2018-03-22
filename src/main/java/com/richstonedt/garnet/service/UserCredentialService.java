package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.UserCredential;
import com.richstonedt.garnet.model.criteria.UserCredentialCriteria;

public interface UserCredentialService extends BaseService<UserCredential, UserCredentialCriteria, Long> {

    UserCredential getCredentialByUserName(String userName);
}