/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.service.impl;

import com.richstonedt.garnet.common.utils.Result;
import com.richstonedt.garnet.modules.sys.dao.SysUserTokenDao;
import com.richstonedt.garnet.modules.sys.entity.SysUserTokenEntity;
import com.richstonedt.garnet.modules.sys.oauth2.TokenGenerator;
import com.richstonedt.garnet.modules.sys.service.SysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * The type Sys user token service.
 *
 * @since garnet-core-be-fe 1.0.0
 */
@Service("sysUserTokenService")
public class SysUserTokenServiceImpl implements SysUserTokenService {

	/**
	 * The Sys user token dao.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Autowired
	private SysUserTokenDao sysUserTokenDao;

	/**
	 * The constant EXPIRE. 3小时后过期
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	private final static int EXPIRE = 3600 * 3;

	/**
	 * Query by user id sys user token entity.
	 *
	 * @param userId the user id
	 * @return the sys user token entity
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public SysUserTokenEntity queryByUserId(Long userId) {
		return sysUserTokenDao.queryByUserId(userId);
	}

	/**
	 * Query by token sys user token entity.
	 *
	 * @param token the token
	 * @return the sys user token entity
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public SysUserTokenEntity queryByToken(String token) {
		return sysUserTokenDao.queryByToken(token);
	}

	/**
	 * Save.
	 *
	 * @param token the token
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public void save(SysUserTokenEntity token){
		sysUserTokenDao.save(token);
	}

	/**
	 * Update.
	 *
	 * @param token the token
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public void update(SysUserTokenEntity token){
		sysUserTokenDao.update(token);
	}

	/**
	 * 生成token
	 *
	 * @param userId  用户ID
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public Result createToken(long userId) {
		//生成一个token
		String token = TokenGenerator.generateValue();

		//当前时间
		Date now = new Date();
		//过期时间
		Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

		//判断是否生成过token
		SysUserTokenEntity tokenEntity = queryByUserId(userId);
		if(tokenEntity == null){
			tokenEntity = new SysUserTokenEntity();
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//保存token
			save(tokenEntity);
		}else{
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			//更新token
			update(tokenEntity);
		}
		return Result.ok().put("garnetToken", token).put("expire", EXPIRE);
	}
}
