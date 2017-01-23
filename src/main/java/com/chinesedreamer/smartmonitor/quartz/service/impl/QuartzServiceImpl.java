package com.chinesedreamer.smartmonitor.quartz.service.impl;

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.chinesedreamer.smartmonitor.domain.dao.quartz.QuartzJobDao;
import com.chinesedreamer.smartmonitor.domain.model.quartz.QuartzJob;
import com.chinesedreamer.smartmonitor.domain.query.quartz.QuartzJobQuery;
import com.chinesedreamer.smartmonitor.quartz.service.QuartzService;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 22, 2017
**/
@Service
public class QuartzServiceImpl implements QuartzService{
	private Logger logger = LoggerFactory.getLogger(QuartzServiceImpl.class);
	
	@Autowired
	private SchedulerFactoryBean schedulerFactory;
	@Autowired
	private BeanFactory schedulerBeanFactory;
	@Resource
	private QuartzJobDao quartzJobDao;

	@Override
	public JobDetail getJobDetail(String name, String group) {
		JobKey jobKey = new JobKey(name, group);
		try {
			JobDetail jobDetail = schedulerFactory.getScheduler().getJobDetail(jobKey);
			return jobDetail;
		} catch (SchedulerException e) {
			this.logger.error("{}",e);
		}
		return null;
	}
	

	@Override
	public void addJob(QuartzJob job) {
		try {
			JobDetail jobDetail = schedulerFactory.getScheduler().getJobDetail(new JobKey(job.getName(), job.getGroupCode()));
			if (null != jobDetail) {
				this.logger.info("Quartz job already exist of group:{}, name:{}.",job.getGroupCode(), job.getName());
				return;
			}
			
			jobDetail = this.getJobDetail(job);
			if (null == jobDetail) {
				this.logger.info("Missing implement for quartz job already exist of group:{}, name:{}. Implement bean:{}, method:{}.",
						job.getGroupCode(), job.getName(),job.getBeanName(),job.getExecuteMethod());
				return;
			}
			CronTrigger cronTrigger = this.getCronTrigger(job,jobDetail);
			schedulerFactory.getScheduler().addJob(jobDetail, true);
			schedulerFactory.getScheduler().scheduleJob(cronTrigger);
			if (job.getStartNow() && null == job.getStartTime()) {
				cronTrigger.getTriggerBuilder().startNow();
			}
		} catch (Exception e) {
			this.logger.error("{}",e);
		}
	}

	@Override
	public void removeJob(QuartzJob job) {
		try {
			JobKey jobKey = new JobKey(job.getName(), job.getGroupCode());
			JobDetail jobDetail = schedulerFactory.getScheduler().getJobDetail(jobKey);
			if (null == jobDetail) {
				this.logger.info("Quartz job not exist of group:{}, name:{}.",job.getGroupCode(), job.getName());
				return;
			}
			schedulerFactory.getScheduler().pauseTrigger(new TriggerKey(job.getName(), job.getGroupCode()));
			schedulerFactory.getScheduler().deleteJob(jobKey);
		} catch (Exception e) {
			this.logger.error("{}",e);
		}
	}

	@Override
	public void stopJob(QuartzJob job) {
		try {
			JobKey jobKey = new JobKey(job.getName(), job.getGroupCode());
			JobDetail jobDetail = schedulerFactory.getScheduler().getJobDetail(jobKey);
			if (null == jobDetail) {
				this.logger.info("Quartz job not exist of group:{}, name:{}.",job.getGroupCode(), job.getName());
				return;
			}
			schedulerFactory.getScheduler().pauseTrigger(new TriggerKey(job.getName(), job.getGroupCode()));
			schedulerFactory.getScheduler().pauseJob(jobKey);
		} catch (Exception e) {
			this.logger.error("{}",e);
		}
	}

	@Override
	public void resumeJob(QuartzJob job) {
		try {
			JobKey jobKey = new JobKey(job.getName(), job.getGroupCode());
			JobDetail jobDetail = schedulerFactory.getScheduler().getJobDetail(jobKey);
			if (null == jobDetail) {
				this.logger.info("Quartz job not exist of group:{}, name:{}.",job.getGroupCode(), job.getName());
				return;
			}

			schedulerFactory.getScheduler().resumeJob(jobKey);
			schedulerFactory.getScheduler().resumeTrigger(new TriggerKey(job.getName(), job.getGroupCode()));
		} catch (Exception e) {
			this.logger.error("{}",e);
		}
	}

	@Override
	public void reschedule(QuartzJob job) {
		try {
			JobKey jobKey = new JobKey(job.getName(), job.getGroupCode());
			JobDetail jobDetail = schedulerFactory.getScheduler().getJobDetail(jobKey);
			if (null == jobDetail) {
				this.logger.info("Quartz job not exist of group:{}, name:{}.",job.getGroupCode(), job.getName());
				return;
			}
			
			TriggerKey triggerKey = new TriggerKey(job.getName(), job.getGroupCode());
			CronTrigger newTrigger = this.getCronTrigger(job,jobDetail);
			schedulerFactory.getScheduler().rescheduleJob(triggerKey, newTrigger);
			if (job.getStartNow() && null == job.getStartTime()) {
				newTrigger.getTriggerBuilder().startNow();
			}
		} catch (Exception e) {
			this.logger.error("{}",e);
		}
	}

	private JobDetail getJobDetail(QuartzJob job){
		try {
			MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
			jobDetailFactoryBean.setGroup(job.getGroupCode());
			jobDetailFactoryBean.setName(job.getName());
			jobDetailFactoryBean.setBeanFactory(this.schedulerBeanFactory);
			jobDetailFactoryBean.setTargetBeanName(job.getBeanName());
			jobDetailFactoryBean.setTargetMethod(job.getExecuteMethod());
			jobDetailFactoryBean.setConcurrent(job.getConcurrent());
			jobDetailFactoryBean.afterPropertiesSet();
			return jobDetailFactoryBean.getObject();
		} catch (Exception e) {
			this.logger.error("{}",e);
			return null;
		}
	}
	
	private CronTrigger getCronTrigger(QuartzJob job, JobDetail jobDetail){
		CronTriggerFactoryBean cronTrigger = new CronTriggerFactoryBean();
		cronTrigger.setGroup(job.getGroupCode());
		cronTrigger.setName(job.getName());
		cronTrigger.setJobDetail(jobDetail);
		cronTrigger.setCronExpression(job.getCronExpression());
		try {
			cronTrigger.afterPropertiesSet();
		} catch (ParseException e) {
			this.logger.error("{}",e);
		}
		
		if (null != job.getStartTime()) {
			cronTrigger.getObject().getTriggerBuilder().startAt(job.getStartTime());
		}
		if (null != job.getEndTime()) {
			cronTrigger.getObject().getTriggerBuilder().endAt(job.getEndTime());
		}
		
		return cronTrigger.getObject();
	}


	@Override
	public List<QuartzJob> findAll(QuartzJobQuery query) {
		return this.quartzJobDao.findAll(query);
	}


	@Override
	public void update(QuartzJob job) {
		this.quartzJobDao.update(job);
	}
}
