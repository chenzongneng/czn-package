/*
 * 广州丰石科技有限公司拥有本软件版权2017并保留所有权利。
 * Copyright 2017, Guangzhou Rich Stone Data Technologies Company Limited,
 * All rights reserved.
 */

package com.richstonedt.garnet.common.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月4日 下午12:59:00
 * @since garnet-core-be-fe 1.0.0
 */
public class PageUtils implements Serializable {

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
	private List<?> list;
	
	/**
	 * 分页
	 * @param list        列表数据
	 * @param totalCount  总记录数
	 * @param pageSize    每页记录数
	 * @param currPage    当前页数
	 * @since garnet-core-be-fe 1.0.0
	 */
	public PageUtils(List<?> list, int totalCount, int pageSize, int currPage) {
		this.list = list;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
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
	public List<?> getList() {
		return list;
	}

	/**
	 * Sets list.
	 *
	 * @param list the list
	 * @since garnet-core-be-fe 1.0.0
	 */
	public void setList(List<?> list) {
		this.list = list;
	}
	
}
