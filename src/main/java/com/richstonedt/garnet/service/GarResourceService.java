/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarResource;
import com.richstonedt.garnet.model.view.model.GarVMResource;

import java.util.List;
import java.util.Map;

/**
 * <b><code>GarResourceService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 11:44
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
public interface GarResourceService extends BaseService<GarResource> {

    List<GarVMResource> queryResourceListByParams(Map<String, Object> params);

    List<GarVMResource> queryResourceListByAppId(Long appId);

    void saveResource(GarVMResource garVMResource);

    GarVMResource searchResource(Long resourceId);

    void updateResource(GarVMResource garVMResource);

    int queryTotalResourceByParam(Map<String, Object> params);
}
