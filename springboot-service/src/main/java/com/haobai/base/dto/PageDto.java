package com.haobai.base.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 分页dto
 * 
 * @author zjy
 *
 * @param <T>
 */
public class PageDto<T> implements Serializable {
	
	private static final long serialVersionUID = -1921797163835692723L;
	/**
	 * 分页数据列表
	 */
	private List<T> pageData;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 总数
	 */
	private long sum;
	/**
	 * 页码
	 */
	private int pageNumber;
	/**
	 * 每页大小
	 */
	private int pageSize;

	public PageDto() {
	}

	public PageDto(List<T> pageData, int totalPage, Long sum, int pageNumber, int pageSize) {
		this.pageData = pageData;
		this.totalPage = totalPage;
		this.sum = sum;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	public List<T> getPageData() {
		return pageData;
	}

	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}

	public int getTotalPage() {
		if (sum != 0 && pageSize != 0) {
			if (sum % pageSize == 0)
				totalPage = (int) (sum / pageSize);
			else
				totalPage = (int) ((sum / pageSize) + 1);
		}
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public long getSum() {
		return sum;
	}

	public void setSum(long sum) {
		this.sum = sum;
	}

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
