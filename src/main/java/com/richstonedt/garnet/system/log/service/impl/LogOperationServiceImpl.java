/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.log.service.impl;

import com.richstonedt.garnet.system.log.dao.LogOperationDao;
import com.richstonedt.garnet.system.log.entity.OperationEntity;
import com.richstonedt.garnet.system.log.service.LogOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <b><code>LogOperationServiceImpl</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/9 14:19
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
@Service
public class LogOperationServiceImpl implements LogOperationService {

    /**
     * The Log operation dao.
     *
     * @since garnet-core-be-fe 0.1.0
     */
    @Autowired
    private LogOperationDao logOperationDao;

    /**
     * Gets all operations.
     *
     * @return the all operations
     * @since garnet-core-be-fe 0.1.0
     */
    @Override
    public List<OperationEntity> getAllOperations() {
        return logOperationDao.getAllOperations();
    }
}
