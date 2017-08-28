/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.authority.dao;

import com.richstonedt.garnet.modules.sys.entity.SysUserEntity;
import com.richstonedt.garnet.system.authority.entity.Authority;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b><code>AuthorityDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/8/25 18:22
 *
 * @author Sun Jinpeng
 * @version 1.0.0
 * @since garnet-core-be-fe 1.0.0
 */
@Mapper
public interface AuthorityDao {

    /**
     * Gets distinct user id.
     *
     * @return the distinct user id
     * @since garnet-core-be-fe 1.0.0
     */
    List<Integer> getDistinctUserId();

    /**
     * Gets authorities by user id.
     *
     * @param userId the user id
     * @return the authorities by user id
     * @since garnet-core-be-fe 1.0.0
     */
    List<Authority> getAuthoritiesByUserId(@Param(value = "userId") Integer userId);

    /**
     * Gets all users.
     *
     * @return the all users
     * @since garnet-core-be-fe 1.0.0
     */
    List<SysUserEntity> getAllUsers();

    /**
     * Save authority.
     *
     * @param userId the user id
     * @param roleId the role id
     * @since garnet-core-be-fe 1.0.0
     */
    void saveAuthority(@Param(value = "userId") Integer userId,@Param(value = "roleId")Integer roleId);

    /**
     * Delete authority by user id.
     *
     * @param userId the user id
     * @since garnet-core-be-fe 1.0.0
     */
    void deleteAuthorityByUserId(@Param(value = "userId") Integer userId);
}
