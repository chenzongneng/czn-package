/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.common.utils;

/**
 * 常量
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月15日 下午1:23:52
 * @since garnet-core-be-fe 1.0.0
 */
public class Constant {
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;

	/**
	 * 菜单类型
	 * 
	 * @author chenshun
	 * @email sunlightcs@gmail.com
	 * @date 2016年11月15日 下午1:24:29
     * @since garnet-core-be-fe 1.0.0
	 */
    public enum MenuType {

        /**
         * 目录
         *
         * @since garnet-core-be-fe 1.0.0
         */
    	CATALOG(0),

        /**
         * 菜单
         *
         * @since garnet-core-be-fe 1.0.0
         */
        MENU(1),

        /**
         * 按钮
         *
         * @since garnet-core-be-fe 1.0.0
         */
        BUTTON(2);

        /**
         * The Value.
         *
         * @since garnet-core-be-fe 1.0.0
         */
        private int value;

        /**
         * Instantiates a new Menu type.
         *
         * @param value the value
         * @since garnet-core-be-fe 1.0.0
         */
        private MenuType(int value) {
            this.value = value;
        }

        /**
         * Gets value.
         *
         * @return the value
         * @since garnet-core-be-fe 1.0.0
         */
        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     * 
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     * @since garnet-core-be-fe 1.0.0
     */
    public enum ScheduleStatus {

        /**
         * 正常
         *
         * @since garnet-core-be-fe 1.0.0
         */
    	NORMAL(0),

        /**
         * 暂停
         *
         * @since garnet-core-be-fe 1.0.0
         */
    	PAUSE(1);

        /**
         * The Value.
         *
         * @since garnet-core-be-fe 1.0.0
         */
        private int value;

        /**
         * Instantiates a new Schedule status.
         *
         * @param value the value
         * @since garnet-core-be-fe 1.0.0
         */
        private ScheduleStatus(int value) {
            this.value = value;
        }

        /**
         * Gets value.
         *
         * @return the value
         * @since garnet-core-be-fe 1.0.0
         */
        public int getValue() {
            return value;
        }
    }
}
