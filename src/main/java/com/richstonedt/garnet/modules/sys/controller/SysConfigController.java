/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.sys.controller;

import com.richstonedt.garnet.common.annotation.SysLog;
import com.richstonedt.garnet.common.utils.PageUtils;
import com.richstonedt.garnet.common.utils.Query;
import com.richstonedt.garnet.common.utils.R;
import com.richstonedt.garnet.common.validator.ValidatorUtils;
import com.richstonedt.garnet.modules.sys.entity.SysConfigEntity;
import com.richstonedt.garnet.modules.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统配置信息
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:55:53
 * @since garnet-core-be-fe 1.0.0
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {

    /**
     * The Sys config service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 所有配置列表
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:config:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<SysConfigEntity> configList = sysConfigService.queryList(query);
        int total = sysConfigService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(configList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
     * 配置信息
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    public R info(@PathVariable("id") Long id) {
        SysConfigEntity config = sysConfigService.queryObject(id);

        return R.ok().put("config", config);
    }

    /**
     * 保存配置
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @SysLog("保存配置")
    @RequestMapping("/save")
    @RequiresPermissions("sys:config:save")
    public R save(@RequestBody SysConfigEntity config) {
        ValidatorUtils.validateEntity(config);

        sysConfigService.save(config);

        return R.ok();
    }

    /**
     * 修改配置
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @SysLog("修改配置")
    @RequestMapping("/update")
    @RequiresPermissions("sys:config:update")
    public R update(@RequestBody SysConfigEntity config) {
        ValidatorUtils.validateEntity(config);

        sysConfigService.update(config);

        return R.ok();
    }

    /**
     * 删除配置
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @SysLog("删除配置")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    public R delete(@RequestBody Long[] ids) {
        sysConfigService.deleteBatch(ids);

        return R.ok();
    }

}
