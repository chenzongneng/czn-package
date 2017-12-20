/*
 * 广州都灵源链信息科技公司有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Torino Source Information Technologies Company Limited,
 * All rights reserved.
 */
package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarAuthority;
import com.richstonedt.garnet.model.view.model.GarVMAuthority;

import java.util.List;

/**
 * <b><code>GarAuthorityService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b>2017/12/6 11:44
 *
 * @author PanXin
 * @version 1.0.0
 * @since garnet-core-be-fe  1.0.0
 */
public interface GarAuthorityService extends BaseService<GarAuthority> {

    List<GarVMAuthority> queryAuthorityList(String searchName, Integer page, Integer limit);

    void saveAuthority(GarVMAuthority garVMAuthority);

    GarVMAuthority searchAuthority(Long authorityId);

    void updateAuthority(GarVMAuthority garVMAuthority);

    List<GarVMAuthority> queryAuthorityListByApplicationId(Long applicationId);
}
