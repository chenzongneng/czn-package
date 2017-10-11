/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.log.dao;

import com.richstonedt.garnet.system.log.entity.OperationEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <b><code>LogOperationDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/9 14:08
 *
 * @author Sun Jinpeng
 * @version 1.0.0
 * @since garnet-core-be-fe 1.0.0
 */
@Mapper
public interface LogOperationDao {

    /**
     * Gets all operations.
     *
     * @return the all operations
     * @since garnet-core-be-fe 1.0.0
     */
    List<OperationEntity> getAllOperations();

}
