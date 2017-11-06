/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarRole;
import com.richstonedt.garnet.model.view.model.GarVMRole;

import java.util.List;

/**
 * <b><code>GarRoleService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/17 10:28
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
public interface GarRoleService extends BaseService<GarRole> {

    /**
     * Query role list list.
     *
     * @param token      the token
     * @param searchName the search name
     * @param page       the page
     * @param limit      the limit
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarVMRole> queryRoleList(String token, String searchName, Integer page, Integer limit);

    /**
     * Save role.
     *
     * @param garVMRole the gar vm role
     * @since garnet-core-be-fe 0.1.0
     */
    void saveRole(GarVMRole garVMRole);

    /**
     * Search role gar vm role.
     *
     * @param roleId the role id
     * @return the gar vm role
     * @since garnet-core-be-fe 0.1.0
     */
    GarVMRole searchRole(Long roleId);

    /**
     * Update role.
     *
     * @param garVMRole the gar vm role
     * @since garnet-core-be-fe 0.1.0
     */
    void updateRole(GarVMRole garVMRole);
}