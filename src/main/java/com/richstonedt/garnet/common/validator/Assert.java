/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.common.validator;

import com.richstonedt.garnet.common.exception.RRException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:50
 * @since garnet-core-be-fe 1.0.0
 */
public abstract class Assert {

    /**
     * Is blank.
     *
     * @param str     the str
     * @param message the message
     * @since garnet-core-be-fe 1.0.0
     */
    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RRException(message);
        }
    }

    /**
     * Is null.
     *
     * @param object  the object
     * @param message the message
     * @since garnet-core-be-fe 1.0.0
     */
    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }
}
