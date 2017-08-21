/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.role.dao;

import com.richstonedt.garnet.system.role.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <b><code>RoleDao</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/8/21 15:22
 *
 * @author Sun Jinpeng
 * @version 1.0.0
 * @since garnet-core-be-fe 1.0.0
 */
@Mapper
public interface RoleDao {

    List<SysRole> searchRoles(int offset, int limit, int roleId);

    void insertRole(SysRole role);
}
