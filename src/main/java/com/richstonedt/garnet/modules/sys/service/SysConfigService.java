/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.service;

import com.richstonedt.garnet.modules.sys.entity.SysConfigEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统配置信息
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:49:01
 * @since garnet-core-be-fe 1.0.0
 */
public interface SysConfigService {
	
	/**
	 * 保存配置信息
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void save(SysConfigEntity config);
	
	/**
	 * 更新配置信息
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void update(SysConfigEntity config);
	
	/**
	 * 根据key，更新value
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void updateValueByKey(String key, String value);
	
	/**
	 * 删除配置信息
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void deleteBatch(Long[] ids);
	
	/**
	 * 获取List列表
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public List<SysConfigEntity> queryList(Map<String, Object> map);

	/**
	 * 获取总记录数
	 *
	 * @since garnet-core-be-fe 1.0.0
	 */
	public int queryTotal(Map<String, Object> map);

	/**
	 * Query object sys config entity.
	 *
	 * @param id the id
	 * @return the sys config entity
	 * @since garnet-core-be-fe 1.0.0
	 */
	public SysConfigEntity queryObject(Long id);
	
	/**
	 * 根据key，获取配置的value值
	 * 
	 * @param key           key
	 * @param defaultValue  缺省值
	 * @since garnet-core-be-fe 1.0.0
	 */
	public String getValue(String key, String defaultValue);
	
	/**
	 * 根据key，获取value的Object对象
	 *
	 * @param key    key
	 * @param clazz  Object对象
	 * @since garnet-core-be-fe 1.0.0
	 */
	public <T> T getConfigObject(String key, Class<T> clazz);
	
}
