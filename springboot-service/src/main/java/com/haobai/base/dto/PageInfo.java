package com.haobai.base.dto;

import java.io.Serializable;

/**
 * 分页信息
 * 
 * @author zjy
 *
 */
public class PageInfo implements Serializable {
	
	private static final long serialVersionUID = 3363610171905349912L;
	/**
	 * 页码
	 */
	private int pageNumber;
	/**
	 * 每页大小
	 */
	private int pageSize;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
