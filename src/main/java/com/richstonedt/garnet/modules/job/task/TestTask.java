/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.job.task;

import com.richstonedt.garnet.modules.sys.entity.SysUserEntity;
import com.richstonedt.garnet.modules.sys.service.SysUserService;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务(演示Demo，可删除)
 * <p>
 * testTask为spring bean的名称
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月30日 下午1:34:24
 * @since garnet-core-be-fe 1.0.0
 */
@Component("testTask")
public class TestTask {

    /**
     * The Logger.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * The Sys user service.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * Test.
     *
     * @param params the params
     * @since garnet-core-be-fe 1.0.0
     */
    public void test(String params) {
        logger.info("我是带参数的test方法，正在被执行，参数为：" + params);

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SysUserEntity user = sysUserService.queryObject(1L);
        System.out.println(ToStringBuilder.reflectionToString(user));

    }

    /**
     * Test.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public void test() {
        logger.info("我是不带参数的test2方法，正在被执行");
    }
}
