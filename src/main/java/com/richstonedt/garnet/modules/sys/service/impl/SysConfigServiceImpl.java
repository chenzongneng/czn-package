/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.service.impl;

import com.google.gson.Gson;
import com.richstonedt.garnet.common.exception.RRException;
import com.richstonedt.garnet.modules.sys.dao.SysConfigDao;
import com.richstonedt.garnet.modules.sys.entity.SysConfigEntity;
import com.richstonedt.garnet.modules.sys.service.SysConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * The type Sys config service.
 *
 * @since garnet-core-be-fe 1.0.0
 */
@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {

	/**
	 * The Sys config dao.
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Autowired
	private SysConfigDao sysConfigDao;

	/**
	 * 保存配置信息
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	@Transactional
	public void save(SysConfigEntity config) {
		sysConfigDao.save(config);
	}

	/**
	 * 更新配置信息
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public void update(SysConfigEntity config) {
		sysConfigDao.update(config);
	}

	/**
	 * 根据key，更新value
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public void updateValueByKey(String key, String value) {
		sysConfigDao.updateValueByKey(key, value);
	}

	/**
	 * 删除配置信息
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public void deleteBatch(Long[] ids) {
		sysConfigDao.deleteBatch(ids);
	}

	/**
	 * 获取List列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public List<SysConfigEntity> queryList(Map<String, Object> map) {
		return sysConfigDao.queryList(map);
	}

	/**
	 * 获取总记录数
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysConfigDao.queryTotal(map);
	}

	/**
	 * Query object sys config entity.
	 *
	 * @param id the id
	 * @return the sys config entity
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public SysConfigEntity queryObject(Long id) {
		return sysConfigDao.queryObject(id);
	}

	/**
	 * 根据key，获取配置的value值
	 *
	 * @param key           key
	 * @param defaultValue  缺省值
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public String getValue(String key, String defaultValue) {
		String value = sysConfigDao.queryByKey(key);
		if(StringUtils.isBlank(value)){
			return defaultValue;
		}
		return value;
	}

	/**
	 * 根据key，获取value的Object对象
	 *
	 * @param key    key
	 * @param clazz  Object对象
	 * @since garnet-core-be-fe 1.0.0
	 */
	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key, null);
		if(StringUtils.isNotBlank(value)){
			return new Gson().fromJson(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RRException("获取参数失败");
		}
	}
}
