/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.common.utils;

import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * <b><code>PageUtil</code></b>
 * <p> 分页工具类
 * class_comment
 * </p>
 * <b>Create Time:</b> 2017/10/16 17:29
 *
 * @author Sun Jinpeng
 * @version 0.1.0
 * @since garnet-core-be-fe 0.1.0
 */
public class PageUtil<T> implements Serializable {

    /**
     * The constant serialVersionUID.
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Total count.  总记录数
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private int totalCount;

    /**
     * The Page size.  每页记录数
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private int pageSize;

    /**
     * The Total page. 总页数
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private int totalPage;

    /**
     * The Curr page.  当前页数
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private int currPage;

    /**
     * The List.  列表数据
     *
     * @since garnet-core-be-fe 1.0.0
     */
    private List<T> list;

    /**
     * 分页
     *
     * @param list       列表数据
     * @param totalCount 总记录数
     * @param pageSize   每页记录数
     * @param currPage   当前页数
     * @since garnet-core-be-fe 1.0.0
     */
    public PageUtil(List<T> list, int totalCount, int pageSize, int currPage) {

        //(currPage+1-1)*pageSize
        int startPage = currPage;

       List<T> tmp = new LinkedList<>();

        if (!CollectionUtils.isEmpty(list)) {
            int count= 0;
            for(int i = 0; i< list.size();i++){

                if(count == pageSize){
                    break;
                }

                else if(i>=(startPage-1)*pageSize){
                    tmp.add(list.get(i));
                    count++;
                }
            }
        }

        this.list = tmp;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    /**
     * Gets total count.
     *
     * @return the total count
     * @since garnet-core-be-fe 1.0.0
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * Sets total count.
     *
     * @param totalCount the total count
     * @since garnet-core-be-fe 1.0.0
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * Gets page size.
     *
     * @return the page size
     * @since garnet-core-be-fe 1.0.0
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets page size.
     *
     * @param pageSize the page size
     * @since garnet-core-be-fe 1.0.0
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Gets total page.
     *
     * @return the total page
     * @since garnet-core-be-fe 1.0.0
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * Sets total page.
     *
     * @param totalPage the total page
     * @since garnet-core-be-fe 1.0.0
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * Gets curr page.
     *
     * @return the curr page
     * @since garnet-core-be-fe 1.0.0
     */
    public int getCurrPage() {
        return currPage;
    }

    /**
     * Sets curr page.
     *
     * @param currPage the curr page
     * @since garnet-core-be-fe 1.0.0
     */
    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    /**
     * Gets list.
     *
     * @return the list
     * @since garnet-core-be-fe 1.0.0
     */
    public List<T> getList() {
        return list;
    }

    /**
     * Sets list.
     *
     * @param list the list
     * @since garnet-core-be-fe 1.0.0
     */
    public void setList(List<T> list) {
        this.list = list;
    }

}
