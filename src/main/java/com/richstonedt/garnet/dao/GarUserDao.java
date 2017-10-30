/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.dao;

import com.richstonedt.garnet.model.GarUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b><code>GarUserDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/17 9:45
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Mapper
public interface GarUserDao extends BaseDao<GarUser> {

    /**
     * Gat user by name gar user.
     *
     * @param userName the user name
     * @return the gar user
     * @since garnet-core-be-fe 0.1.0
     */
    GarUser getUserByName(@Param(value = "userName") String userName);

    /**
     * Query user list list.
     *
     * @param tenantId   the tenant Id
     * @param searchName the search name
     * @param limit      the limit
     * @param offset     the offset
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarUser> queryUserList(@Param(value = "tenantId") Long tenantId, @Param(value = "searchName") String searchName,
                                @Param(value = "limit") Integer limit, @Param(value = "offset") Integer offset);
}
