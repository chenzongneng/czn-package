/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.job.entity;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时器
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月28日 下午12:54:44
 * @since garnet-core-be-fe 1.0.0
 */
public class ScheduleJobEntity implements Serializable {

    /**
     * The constant serialVersionUID.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private static final long serialVersionUID = 1L;

    /**
     * 任务调度参数key
     *
     * @since garnet-core-be-fe 1.0.0
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    /**
     * 任务id
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Long jobId;

    /**
     * spring bean名称
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @NotBlank(message = "bean名称不能为空")
    private String beanName;

    /**
     * 方法名
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @NotBlank(message = "方法名称不能为空")
    private String methodName;

    /**
     * 参数
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private String params;

    /**
     * cron表达式
     *
     * @since garnet-core-be-fe 1.0.0
     */
    @NotBlank(message = "cron表达式不能为空")
    private String cronExpression;

    /**
     * 任务状态
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Integer status;

    /**
     * 备注
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private String remark;

    /**
     * 创建时间
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Date createTime;

    /**
     * 设置：任务id
     *
     * @param jobId 任务id
     * @since garnet-core-be-fe 1.0.0
     */
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    /**
     * 获取：任务id
     *
     * @return Long
     * @since garnet-core-be-fe 1.0.0
     */
    public Long getJobId() {
        return jobId;
    }

    /**
     * Gets bean name.
     *
     * @return the bean name
     * @since garnet-core-be-fe 1.0.0
     */
    public String getBeanName() {
        return beanName;
    }

    /**
     * Sets bean name.
     *
     * @param beanName the bean name
     * @since garnet-core-be-fe 1.0.0
     */
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    /**
     * Gets method name.
     *
     * @return the method name
     * @since garnet-core-be-fe 1.0.0
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Sets method name.
     *
     * @param methodName the method name
     * @since garnet-core-be-fe 1.0.0
     */
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    /**
     * Gets params.
     *
     * @return the params
     * @since garnet-core-be-fe 1.0.0
     */
    public String getParams() {
        return params;
    }

    /**
     * Sets params.
     *
     * @param params the params
     * @since garnet-core-be-fe 1.0.0
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * Gets remark.
     *
     * @return the remark
     * @since garnet-core-be-fe 1.0.0
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets remark.
     *
     * @param remark the remark
     * @since garnet-core-be-fe 1.0.0
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 设置：任务状态
     *
     * @param status 任务状态
     * @since garnet-core-be-fe 1.0.0
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取：任务状态
     *
     * @return String
     * @since garnet-core-be-fe 1.0.0
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置：cron表达式
     *
     * @param cronExpression cron表达式
     * @since garnet-core-be-fe 1.0.0
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * 获取：cron表达式
     *
     * @return String
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * 设置：创建时间
     *
     * @param createTime 创建时间
     * @since garnet-core-be-fe 1.0.0
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取：创建时间
     *
     * @return Date
     * @since garnet-core-be-fe 1.0.0
     */
    public Date getCreateTime() {
        return createTime;
    }
}
