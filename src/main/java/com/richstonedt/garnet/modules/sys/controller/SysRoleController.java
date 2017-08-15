/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.controller;

import com.richstonedt.garnet.common.annotation.SysLog;
import com.richstonedt.garnet.common.utils.Constant;
import com.richstonedt.garnet.common.utils.PageUtils;
import com.richstonedt.garnet.common.utils.Query;
import com.richstonedt.garnet.common.utils.Result;
import com.richstonedt.garnet.common.validator.ValidatorUtils;
import com.richstonedt.garnet.modules.sys.entity.SysRoleEntity;
import com.richstonedt.garnet.modules.sys.service.SysRoleDeptService;
import com.richstonedt.garnet.modules.sys.service.SysRoleMenuService;
import com.richstonedt.garnet.modules.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月8日 下午2:18:33
 * @since garnet-core-be-fe 1.0.0
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {

    /**
     * The Sys role service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * The Sys role menu service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * The Sys role dept service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private SysRoleDeptService sysRoleDeptService;

    /**
     * 角色列表
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public Result list(@RequestParam Map<String, Object> params) {
        //如果不是超级管理员，则只查询自己创建的角色列表
        if (getUserId() != Constant.SUPER_ADMIN) {
            params.put("createUserId", getUserId());
        }

        //查询列表数据
        Query query = new Query(params);
        List<SysRoleEntity> list = sysRoleService.queryList(query);
        int total = sysRoleService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());

        return Result.ok().put("page", pageUtil);
    }

    /**
     * 角色列表
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    public Result select() {
        Map<String, Object> map = new HashMap<>();

        //如果不是超级管理员，则只查询自己所拥有的角色列表
        if (getUserId() != Constant.SUPER_ADMIN) {
            map.put("createUserId", getUserId());
        }
        List<SysRoleEntity> list = sysRoleService.queryList(map);

        return Result.ok().put("list", list);
    }

    /**
     * 角色信息
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public Result info(@PathVariable("roleId") Long roleId) {
        SysRoleEntity role = sysRoleService.queryObject(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        //查询角色对应的部门
        List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(roleId);
        role.setDeptIdList(deptIdList);

        return Result.ok().put("role", role);
    }

    /**
     * 保存角色
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @SysLog("保存角色")
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public Result save(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role);

        sysRoleService.save(role);

        return Result.ok();
    }

    /**
     * 修改角色
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @SysLog("修改角色")
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public Result update(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role);

        sysRoleService.update(role);

        return Result.ok();
    }

    /**
     * 删除角色
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @SysLog("删除角色")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public Result delete(@RequestBody Long[] roleIds) {
        sysRoleService.deleteBatch(roleIds);

        return Result.ok();
    }
}
