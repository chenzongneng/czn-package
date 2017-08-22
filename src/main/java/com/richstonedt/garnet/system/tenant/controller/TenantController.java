/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.system.tenant.controller;

import com.richstonedt.garnet.common.utils.GarnetUtils;
import com.richstonedt.garnet.system.tenant.entity.Tenant;
import com.richstonedt.garnet.system.tenant.service.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <b><code>TenantController</code></b>
 * <p/>
 * TenantController
 * <p/>
 * <b>Creation Time:</b> 2017/8/22 11:12.
 *
 * @author chenzechao
 * @since Garnet 1.0.0
 */
@RestController
@RequestMapping(value = "/v1.0")
public class TenantController {

    /**
     * The constant LOG.
     *
     * @since Garnet 1.0.0
     */
    private final static Logger LOG = LoggerFactory.getLogger(TenantController.class);

    /**
     * The Tenant service.
     *
     * @since Garnet 1.0.0
     */
    private final TenantService tenantService;

    @Autowired
    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    /**
     * Gets tenant list.
     *
     * @return the tenant list
     */
    @RequestMapping(value = "/tenants", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getTenantList() {
        try {
            List<Tenant> tenants = tenantService.getTenantList();
            return new ResponseEntity<>(tenants, HttpStatus.OK);
        } catch (Throwable t) {
            LOG.error("Failed to get tenants!", t);
            return GarnetUtils.newResponseEntity(t);
        }
    }
}
