/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.controller;

import com.richstonedt.garnet.common.utils.Constant;
import com.richstonedt.garnet.common.utils.Result;
import com.richstonedt.garnet.modules.sys.entity.SysDeptEntity;
import com.richstonedt.garnet.modules.sys.service.SysDeptService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 部门管理
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-06-20 15:23:47
 * @since garnet-core-be-fe 1.0.0
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController extends AbstractController {

    /**
     * The Sys dept service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 列表
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dept:list")
    public List<SysDeptEntity> list() {
        Map<String, Object> map = new HashMap<>();
        //如果不是超级管理员，则只能查询本部门及子部门数据
        if (getUserId() != Constant.SUPER_ADMIN) {
            map.put("deptFilter", sysDeptService.getSubDeptIdList(getDeptId()));
        }
        return sysDeptService.queryList(map);
    }

    /**
     * 选择部门(添加、修改菜单)
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:dept:select")
    public Result select() {
        Map<String, Object> map = new HashMap<>();
        //如果不是超级管理员，则只能查询本部门及子部门数据
        if (getUserId() != Constant.SUPER_ADMIN) {
            map.put("deptFilter", sysDeptService.getSubDeptIdList(getDeptId()));
        }
        List<SysDeptEntity> deptList = sysDeptService.queryList(map);

        //添加一级部门
        if (getUserId() == Constant.SUPER_ADMIN) {
            SysDeptEntity root = new SysDeptEntity();
            root.setDeptId(0L);
            root.setName("一级部门");
            root.setParentId(-1L);
            root.setOpen(true);
            deptList.add(root);
        }

        return Result.ok().put("deptList", deptList);
    }

    /**
     * 上级部门Id(管理员则为0)
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping("/info")
    @RequiresPermissions("sys:dept:list")
    public Result info() {
        long deptId = 0;
        if (getUserId() != Constant.SUPER_ADMIN) {
            SysDeptEntity dept = sysDeptService.queryObject(getDeptId());
            deptId = dept.getParentId();
        }

        return Result.ok().put("deptId", deptId);
    }

    /**
     * 信息
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping("/info/{deptId}")
    @RequiresPermissions("sys:dept:info")
    public Result info(@PathVariable("deptId") Long deptId) {
        SysDeptEntity dept = sysDeptService.queryObject(deptId);

        return Result.ok().put("dept", dept);
    }

    /**
     * 保存
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dept:save")
    public Result save(@RequestBody SysDeptEntity dept) {
        sysDeptService.save(dept);

        return Result.ok();
    }

    /**
     * 修改
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dept:update")
    public Result update(@RequestBody SysDeptEntity dept) {
        sysDeptService.update(dept);

        return Result.ok();
    }

    /**
     * 删除
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dept:delete")
    public Result delete(long deptId) {
        //判断是否有子部门
        List<Long> deptList = sysDeptService.queryDetpIdList(deptId);
        if (deptList.size() > 0) {
            return Result.error("请先删除子部门");
        }

        sysDeptService.delete(deptId);

        return Result.ok();
    }

}
