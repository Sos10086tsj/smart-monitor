package com.chinesedreamer.smartmonitor.domain.query.quartz;

import com.chinesedreamer.smartmonitor.domain.query.BaseQuery;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 22, 2017
**/
public class QuartzJobQuery extends BaseQuery{
	private Boolean needRefresh = Boolean.FALSE;

	public Boolean getNeedRefresh() {
		return needRefresh;
	}

	public void setNeedRefresh(Boolean needRefresh) {
		this.needRefresh = needRefresh;
	}
	
	
}
