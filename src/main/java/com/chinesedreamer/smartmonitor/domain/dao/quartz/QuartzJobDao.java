package com.chinesedreamer.smartmonitor.domain.dao.quartz;

import java.util.List;

import com.chinesedreamer.smartmonitor.domain.model.quartz.QuartzJob;
import com.chinesedreamer.smartmonitor.domain.query.quartz.QuartzJobQuery;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 22, 2017
**/
public interface QuartzJobDao {
	public List<QuartzJob> findAll(QuartzJobQuery query);
	public int update(QuartzJob job);
}
