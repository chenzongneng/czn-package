package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.mapper.BaseMapper;
import com.richstonedt.garnet.mapper.UserCredentialMapper;
import com.richstonedt.garnet.model.User;
import com.richstonedt.garnet.model.UserCredential;
import com.richstonedt.garnet.model.criteria.UserCredentialCriteria;
import com.richstonedt.garnet.service.UserCredentialService;
import com.richstonedt.garnet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@Transactional
public class UserCredentialServiceImpl extends BaseServiceImpl<UserCredential, UserCredentialCriteria, Long> implements UserCredentialService {
    @Autowired
    private UserCredentialMapper userCredentialMapper;

    @Autowired
    private UserService userService;

    @Override
    public BaseMapper getBaseMapper() {
        return this.userCredentialMapper;
    }

    @Override
    public UserCredential getCredentialByUserName(String userName) {
        User user = userService.getUserByUserName(userName);
        if (ObjectUtils.isEmpty(user)) {
            return new UserCredential();
        }

        Long userId = user.getId();
        UserCredentialCriteria userCredentialCriteria = new UserCredentialCriteria();
        userCredentialCriteria.createCriteria().andUserIdEqualTo(userId);
        UserCredential userCredential = this.selectSingleByCriteria(userCredentialCriteria);
        return userCredential;
    }
}