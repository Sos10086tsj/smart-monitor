package com.chinesedreamer.smartmonitor.quartz.service;

import java.util.List;

import org.quartz.JobDetail;

import com.chinesedreamer.smartmonitor.domain.model.quartz.QuartzJob;
import com.chinesedreamer.smartmonitor.domain.query.quartz.QuartzJobQuery;

/**
 * Description: 提供quartz job管理接口
 * Auth:Paris
 * Date:Jan 22, 2017
**/
public interface QuartzService {
	public JobDetail getJobDetail(String name, String group);
	
	/**
	 * 添加一个job
	 * @param job
	 */
	public void addJob(QuartzJob job);
	
	/**
	 * 删除一个job
	 * @param job
	 */
	public void removeJob(QuartzJob job);
	
	/**
	 * 停止一个job
	 * @param job
	 */
	public void stopJob(QuartzJob job);
	
	/**
	 * 恢复一个job
	 * @param job
	 */
	public void resumeJob(QuartzJob job);
	
	/**
	 * 调整job的执行时间
	 * @param job
	 */
	public void reschedule(QuartzJob job);
	
	public List<QuartzJob> findAll(QuartzJobQuery query);
	public void update(QuartzJob job);
}
