/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.dao;

import com.richstonedt.garnet.model.GarRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <b><code>GarRoleDao</code></b>
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
public interface GarRoleDao extends BaseDao<GarRole> {

    /**
     * Query role list list.
     *
     * @param tenantId   the tenant Id
     * @param searchName the search name
     * @param limit      the limit
     * @param offset     the offset
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarRole> queryRoleList(@Param(value = "tenantId") Long tenantId, @Param(value = "searchName") String searchName,
                                @Param(value = "limit") Integer limit, @Param(value = "offset") Integer offset);

}
