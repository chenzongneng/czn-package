/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.modules.job.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时执行日志
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月1日 下午10:26:18
 * @since garnet-core-be-fe 1.0.0
 */
public class ScheduleJobLogEntity implements Serializable {

    /**
     * The constant serialVersionUID.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private static final long serialVersionUID = 1L;

    /**
     * 日志id
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Long logId;

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
    private String beanName;

    /**
     * 方法名
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private String methodName;

    /**
     * 参数
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private String params;

    /**
     * 任务状态    0：成功    1：失败
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Integer status;

    /**
     * 失败信息
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private String error;

    /**
     * 耗时(单位：毫秒)
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Integer times;

    /**
     * 创建时间
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private Date createTime;

    /**
     * Gets log id.
     *
     * @return the log id
     * @since garnet-core-be-fe 1.0.0
     */
    public Long getLogId() {
        return logId;
    }

    /**
     * Sets log id.
     *
     * @param logId the log id
     * @since garnet-core-be-fe 1.0.0
     */
    public void setLogId(Long logId) {
        this.logId = logId;
    }

    /**
     * Gets job id.
     *
     * @return the job id
     * @since garnet-core-be-fe 1.0.0
     */
    public Long getJobId() {
        return jobId;
    }

    /**
     * Sets job id.
     *
     * @param jobId the job id
     * @since garnet-core-be-fe 1.0.0
     */
    public void setJobId(Long jobId) {
        this.jobId = jobId;
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
     * Gets status.
     *
     * @return the status
     * @since garnet-core-be-fe 1.0.0
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     * @since garnet-core-be-fe 1.0.0
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * Gets error.
     *
     * @return the error
     * @since garnet-core-be-fe 1.0.0
     */
    public String getError() {
        return error;
    }

    /**
     * Sets error.
     *
     * @param error the error
     * @since garnet-core-be-fe 1.0.0
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * Gets times.
     *
     * @return the times
     * @since garnet-core-be-fe 1.0.0
     */
    public Integer getTimes() {
        return times;
    }

    /**
     * Sets times.
     *
     * @param times the times
     * @since garnet-core-be-fe 1.0.0
     */
    public void setTimes(Integer times) {
        this.times = times;
    }

    /**
     * Gets create time.
     *
     * @return the create time
     * @since garnet-core-be-fe 1.0.0
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Sets create time.
     *
     * @param createTime the create time
     * @since garnet-core-be-fe 1.0.0
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
