/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.service;

import com.richstonedt.garnet.model.GarApplication;
import com.richstonedt.garnet.model.view.model.GarVMApplication;

import java.util.List;

/**
 * <b><code>GarApplicationService</code></b>
 * <p>
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/17 10:28
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
public interface GarApplicationService extends BaseService<GarApplication> {

    /**
     * Save application.
     *
     * @param vmApplication the vm application
     * @since garnet-core-be-fe 0.1.0
     */
    void saveApplication(GarVMApplication vmApplication);

    /**
     * Gets vm application by app id.
     *
     * @param appId the app id
     * @return the vm application by app id
     * @since garnet-core-be-fe 0.1.0
     */
    GarVMApplication getVmApplicationByAppId(Long appId);

    /**
     * Query vm applications list.
     *
     * @param searchName the search name
     * @param page       the page
     * @param limit      the limit
     * @return the list
     * @since garnet-core-be-fe 0.1.0
     */
    List<GarVMApplication> queryVmApplications(String searchName, Integer page, Integer limit);

    /**
     * Update vm application.
     *
     * @param vmApplication the vm application
     * @since garnet-core-be-fe 0.1.0
     */
    void updateVmApplication(GarVMApplication vmApplication);
}
