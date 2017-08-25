/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.authority.service.impl;

import com.richstonedt.garnet.modules.sys.service.SysUserService;
import com.richstonedt.garnet.system.authority.dao.AuthorityDao;
import com.richstonedt.garnet.system.authority.entity.Authority;
import com.richstonedt.garnet.system.authority.entity.viewModel.UserRoles;
import com.richstonedt.garnet.system.authority.service.AuthorityService;
import com.richstonedt.garnet.system.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <b><code>AuthorityServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/8/25 18:10
 *
 * @author Sun Jinpeng
 * @version 1.0.0
 * @since garnet-core-be-fe 1.0.0
 */
@Service
public class AuthorityServiceImpl implements AuthorityService{

    /**
     * The Authority dao.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private AuthorityDao authorityDao;

    /**
     * The Role service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private RoleService roleService;

    /**
     * The User service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private SysUserService userService;

    /**
     * Gets user roles list.
     *
     * @return the user roles list
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public List<UserRoles> getUserRolesList() {
        List<UserRoles> finalResult = new ArrayList<>();
        List<Integer>  userIds = authorityDao.getDistinctUserId();
        if(!CollectionUtils.isEmpty(userIds)){
            for(Integer userId : userIds){
                UserRoles userRoles =  new UserRoles();
                List<Authority> authorityList = authorityDao.getAuthoritiesByUserId(userId);
                List<String> roles = new ArrayList<>();
                if(!CollectionUtils.isEmpty(authorityList)) {
                    for (Authority authority : authorityList) {
                        roles.add(roleService.getRoleById(authority.getRoleId()).getName());
                    }
                }
                String userName = userService.queryObject(userId.longValue()).getUsername();
                userRoles.setUserId(userId);
                userRoles.setUserName(userName);
                userRoles.setRoles(roles);
                finalResult.add(userRoles);
            }
        }
        return finalResult;
    }
}
