/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarSysMenu;

import java.util.List;

/**
 * <b><code>GarSysMenuService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/19 17:03
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
public interface GarSysMenuService {

    /**
     * Gets user menu list.
     *
     * @return the user menu list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarSysMenu> getUserMenuList();
}
