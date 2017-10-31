/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarLogOperation;

import java.util.List;

/**
 * <b><code>GarLogOperationService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/31 18:08
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
public interface GarLogOperationService extends BaseService<GarLogOperation> {

    /**
     * Gets all operations.
     *
     * @return the all operations
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarLogOperation> getAllOperations();
}
