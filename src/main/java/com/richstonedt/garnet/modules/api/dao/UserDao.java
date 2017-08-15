/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.api.dao;

import com.richstonedt.garnet.modules.api.entity.UserEntity;
import com.richstonedt.garnet.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:06
 * @since garnet-core-be-fe 1.0.0
 */
@Mapper
public interface UserDao extends BaseDao<UserEntity> {

    /**
     * Query by mobile user entity.
     *
     * @param mobile the mobile
     * @return the user entity
     * @since garnet-core-be-fe 1.0.0
     */
    UserEntity queryByMobile(String mobile);

}
