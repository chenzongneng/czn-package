/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service.impl;

import com.richstonedt.garnet.model.GarToken;
import com.richstonedt.garnet.model.GarUser;
import com.richstonedt.garnet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <b><code>GarShiroServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/4 15:42
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
@Service
public class GarShiroServiceImpl implements GarShiroService {

    @Autowired
    private GarTokenService tokenService;
    @Autowired
    private GarUserService userService;
    @Autowired
    private GarUserDeptService userDeptService;
    @Autowired
    private GarRoleDeptService roleDeptService;
    @Autowired
    private GarRoleAuthorityService roleAuthorityService;
    @Autowired
    private GarAuthorityPermissionService authorityPermissionService;
    @Autowired
    private GarPermissionService permissionService;

    @Override
    public Set<String> getUserPermissions(long userId) {
        Set<Long> deptIds = userDeptService.getDeptIdsByUserId(userId);
        Set<Long> roleIds = roleDeptService.getRoleIdsByDeptIds(deptIds);
        System.out.println("TEST:deptIds:  "+deptIds);
        Set<Long> authorityIds = roleAuthorityService.getAuthorityIdsByRoleIds(roleIds);
        Set<Long> permissionIds = authorityPermissionService.getPermissionIdsByAuthorityIds(authorityIds);
        return permissionService.getPermissionsByIds(permissionIds);
    }

    @Override
    public GarToken queryByToken(String token) {
        GarToken garToken = tokenService.queryByToken(token);
        return garToken;
    }

    @Override
    public GarUser queryUser(Long userId) {
        return userService.searchUser(userId);
    }
}
