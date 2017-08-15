/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.service.impl;

import com.richstonedt.garnet.common.utils.Constant;
import com.richstonedt.garnet.modules.sys.dao.SysMenuDao;
import com.richstonedt.garnet.modules.sys.dao.SysUserDao;
import com.richstonedt.garnet.modules.sys.dao.SysUserTokenDao;
import com.richstonedt.garnet.modules.sys.entity.SysMenuEntity;
import com.richstonedt.garnet.modules.sys.entity.SysUserEntity;
import com.richstonedt.garnet.modules.sys.entity.SysUserTokenEntity;
import com.richstonedt.garnet.modules.sys.service.ShiroService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * The type Shiro service.
 *
 * @since garnet-core-be-fe 1.0.0
 */
@Service
public class ShiroServiceImpl implements ShiroService {

    /**
     * The Sys menu dao.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private SysMenuDao sysMenuDao;

    /**
     * The Sys user dao.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private SysUserDao sysUserDao;

    /**
     * The Sys user token dao.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private SysUserTokenDao sysUserTokenDao;

    /**
     * 获取用户权限列表
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            List<SysMenuEntity> menuList = sysMenuDao.queryList(new HashMap<String, Object>());
            permsList = new ArrayList<>(menuList.size());
            for(SysMenuEntity menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    /**
     * Query by token sys user token entity.
     *
     * @param token the token
     * @return the sys user token entity
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public SysUserTokenEntity queryByToken(String token) {
        return sysUserTokenDao.queryByToken(token);
    }

    /**
     * 根据用户ID，查询用户
     *
     * @param userId the user id
     * @since garnet-core-be-fe 1.0.0
     */
    @Override
    public SysUserEntity queryUser(Long userId) {
        return sysUserDao.queryObject(userId);
    }
}
