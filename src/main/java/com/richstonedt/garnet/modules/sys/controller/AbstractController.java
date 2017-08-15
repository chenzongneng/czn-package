/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.controller;

import com.richstonedt.garnet.modules.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月9日 下午9:42:26
 * @since garnet-core-be-fe 1.0.0
 */
public abstract class AbstractController {

    /**
     * The Logger.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Gets user.
     *
     * @return the user
     * @since garnet-core-be-fe 1.0.0
     */
    protected SysUserEntity getUser() {
        return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * Gets user id.
     *
     * @return the user id
     * @since garnet-core-be-fe 1.0.0
     */
    protected Long getUserId() {
        return getUser().getUserId();
    }

    /**
     * Gets dept id.
     *
     * @return the dept id
     * @since garnet-core-be-fe 1.0.0
     */
    protected Long getDeptId() {
        return getUser().getDeptId();
    }
}
