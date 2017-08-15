/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.common.utils;

import com.richstonedt.garnet.common.xss.SQLFilter;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 查询参数
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-14 23:15
 * @since garnet-core-be-fe 1.0.0
 */
public class Query extends LinkedHashMap<String, Object> {

    /**
     * The constant serialVersionUID.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Page. 当前页码
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private int page;

    /**
     * The Limit. 每页条数
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private int limit;

    /**
     * Instantiates a new Query.
     *
     * @param params the params
     * @since garnet-core-be-fe 1.0.0
     */
    public Query(Map<String, Object> params){
        this.putAll(params);

        //分页参数
        this.page = Integer.parseInt(params.get("page").toString());
        this.limit = Integer.parseInt(params.get("limit").toString());
        this.put("offset", (page - 1) * limit);
        this.put("page", page);
        this.put("limit", limit);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = (String)params.get("sidx");
        String order = (String)params.get("order");
        if(StringUtils.isNotBlank(sidx)){
            this.put("sidx", SQLFilter.sqlInject(sidx));
        }
        if(StringUtils.isNotBlank(order)){
            this.put("order", SQLFilter.sqlInject(order));
        }

    }


    /**
     * Gets page.
     *
     * @return the page
     * @since garnet-core-be-fe 1.0.0
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets page.
     *
     * @param page the page
     * @since garnet-core-be-fe 1.0.0
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Gets limit.
     *
     * @return the limit
     * @since garnet-core-be-fe 1.0.0
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Sets limit.
     *
     * @param limit the limit
     * @since garnet-core-be-fe 1.0.0
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }
}
