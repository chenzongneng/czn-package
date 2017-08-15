/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.service;

import com.richstonedt.garnet.common.utils.Result;
import com.richstonedt.garnet.modules.sys.entity.SysUserTokenEntity;

/**
 * 用户Token
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:07
 * @since garnet-core-be-fe 1.0.0
 */
public interface SysUserTokenService {

	/**
	 * Query by user id sys user token entity.
	 *
	 * @param userId the user id
	 * @return the sys user token entity
	 * @since garnet-core-be-fe 1.0.0
	 */
	SysUserTokenEntity queryByUserId(Long userId);

	/**
	 * Query by token sys user token entity.
	 *
	 * @param token the token
	 * @return the sys user token entity
	 * @since garnet-core-be-fe 1.0.0
	 */
	SysUserTokenEntity queryByToken(String token);

	/**
	 * Save.
	 *
	 * @param token the token
	 * @since garnet-core-be-fe 1.0.0
	 */
	void save(SysUserTokenEntity token);

	/**
	 * Update.
	 *
	 * @param token the token
	 * @since garnet-core-be-fe 1.0.0
	 */
	void update(SysUserTokenEntity token);

	/**
	 * 生成token
	 *
	 * @param userId  用户ID
	 * @since garnet-core-be-fe 1.0.0
	 */
	Result createToken(long userId);

}
