/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.authority.service;

import com.richstonedt.garnet.system.authority.entity.viewModel.UserRoles;

import java.util.List;

/**
 * <b><code>AuthorityService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/8/25 18:09
 *
 * @author Sun Jinpeng
 * @version 1.0.0
 * @since garnet-core-be-fe 1.0.0
 */
public interface AuthorityService {

    /**
     * Gets user roles list.
     *
     * @return the user roles list
     * @since garnet-core-be-fe 1.0.0
     */
    List<UserRoles> getUserRolesList(String searchName);
}
