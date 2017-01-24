package com.chinesedreamer.smartmonitor.web.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.chinesedreamer.smartmonitor.domain.model.quartz.QuartzJob;
import com.chinesedreamer.smartmonitor.domain.query.quartz.QuartzJobQuery;
import com.chinesedreamer.smartmonitor.quartz.service.QuartzService;
import com.chinesedreamer.smartmonitor.util.MailUtil;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 22, 2017
**/
public class InitializationListener implements ServletContextListener{
	private Logger logger = LoggerFactory.getLogger(InitializationListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		this.logger.info(" Initialization start...");
		MailUtil.initMailConfigLoading();
		this.initQuartz(sce);
		this.logger.info(" Initialization Finished.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	private void initQuartz(ServletContextEvent sce) {
		this.logger.info("Loading quartz tasks...");
		ApplicationContext application = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		QuartzService quartzService = (QuartzService)application.getBean(QuartzService.class);
		List<QuartzJob> jobs = quartzService.findAll(new QuartzJobQuery());
		for (QuartzJob job : jobs) {
			this.logger.info("Loading task, group:{}, name:{}.", job.getGroupCode(), job.getName());
			quartzService.addJob(job);
		}
		this.logger.info("Quartz tasks loaded");
	}
}
