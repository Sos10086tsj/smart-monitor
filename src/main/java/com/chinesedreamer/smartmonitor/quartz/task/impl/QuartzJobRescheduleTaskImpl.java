package com.chinesedreamer.smartmonitor.quartz.task.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.chinesedreamer.smartmonitor.domain.model.quartz.QuartzJob;
import com.chinesedreamer.smartmonitor.domain.query.quartz.QuartzJobQuery;
import com.chinesedreamer.smartmonitor.quartz.service.QuartzService;
import com.chinesedreamer.smartmonitor.quartz.task.QuartzJobRescheduleTask;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 22, 2017
**/
@Service("quartzJobRescheduleTask")
public class QuartzJobRescheduleTaskImpl implements QuartzJobRescheduleTask{
	private Logger logger = LoggerFactory.getLogger(QuartzJobRescheduleTaskImpl.class);
	
	@Resource
	private QuartzService quartzService;

	@Override
	public void updateQuartzJob() {
		QuartzJobQuery query = new QuartzJobQuery();
		query.setNeedRefresh(Boolean.TRUE);
		List<QuartzJob> quartzJobs = this.quartzService.findAll(query);
		for (QuartzJob quartzJob : quartzJobs) {
			this.logger.info("Reschedule job:{}",quartzJob.toString());
			this.quartzService.reschedule(quartzJob);
			quartzJob.setNeedRefresh(Boolean.FALSE);
			this.quartzService.update(quartzJob);
		}
	}

}
