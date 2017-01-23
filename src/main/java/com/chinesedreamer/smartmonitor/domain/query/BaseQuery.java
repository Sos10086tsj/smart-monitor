package com.chinesedreamer.smartmonitor.domain.query;
/**
 * Description:
 * Auth:Paris
 * Date:Jan 22, 2017
**/
public class BaseQuery {
	public final static Integer DEFAULT_PAGE_NUM = 0;
	public final static Integer DEFAULT_PAGE_SIZE = 20;
	
	protected Integer pageNum;
	protected Integer pageSize;
	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPageNum() {
		if (null == pageNum) {
			setPageNum(DEFAULT_PAGE_NUM);
		}
		return pageNum;
	}

	public Integer getPageSize() {
		if (null == pageSize) {
			setPageSize(DEFAULT_PAGE_SIZE);
		}
		return pageSize;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
