/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarUserDept;

import java.util.List;

/**
 * <b><code>GarUserDeptService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/17 10:28
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
public interface GarUserDeptService extends BaseService<GarUserDept> {

    /**
     * Query object by dept id list.
     *
     * @param deptId the dept id
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarUserDept> queryObjectByDeptId(Long deptId);
}
