/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarToken;

/**
 * <b><code>GarApplicationService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/17 10:28
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
public interface GarTokenService extends BaseService<GarToken> {

    /**
     * Query by token gar token.
     *
     * @param token the token
     * @return the gar token
     * @since garnet-core-be-fe 0.1.0
     */
    GarToken queryByToken(String token);
}
