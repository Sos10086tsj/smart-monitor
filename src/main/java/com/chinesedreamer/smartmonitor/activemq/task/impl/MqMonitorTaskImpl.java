package com.chinesedreamer.smartmonitor.activemq.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.chinesedreamer.smartmonitor.activemq.service.ActiveMqService;
import com.chinesedreamer.smartmonitor.activemq.task.MqMonitorTask;
import com.chinesedreamer.smartmonitor.activemq.vo.MqMonitorBrokerInfoVelocityVo;
import com.chinesedreamer.smartmonitor.activemq.vo.MqMonitorBrokerQueueInfoVelocityVo;
import com.chinesedreamer.smartmonitor.activemq.vo.MqMonitorVelocityVo;
import com.chinesedreamer.smartmonitor.domain.constant.activemq.ActiveMqConfigStatus;
import com.chinesedreamer.smartmonitor.domain.model.activemq.ActiveMqConfiguration;
import com.chinesedreamer.smartmonitor.domain.model.activemq.ActiveMqJmxConfiguration;
import com.chinesedreamer.smartmonitor.domain.model.activemq.BrokerInfo;
import com.chinesedreamer.smartmonitor.domain.model.activemq.BrokerQueueInfo;
import com.chinesedreamer.smartmonitor.domain.query.activemq.ActiveMqJmxConfigurationQuery;
import com.chinesedreamer.smartmonitor.domain.query.activemq.BrokerQueueInfoQuery;
import com.chinesedreamer.smartmonitor.tools.mail.MailSender;
import com.chinesedreamer.smartmonitor.tools.mail.service.MailSenderService;
import com.chinesedreamer.smartmonitor.util.ActiveMqUtil;
import com.chinesedreamer.smartmonitor.util.DateUtil;
import com.chinesedreamer.smartmonitor.util.MathUtil;
import com.chinesedreamer.smartmonitor.util.PropertiesUtil;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 23, 2017
**/
@Service
public class MqMonitorTaskImpl implements MqMonitorTask{
	private Logger logger = LoggerFactory.getLogger(MqMonitorTaskImpl.class);
	@Resource
	private ActiveMqService mqService;
	@Resource
	private MailSenderService mailSenderService;
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Override
	@Scheduled(cron="0 0/10 * * * ?")
	public void monitor() {
		this.logger.info(" Start to monitor MQ task.");
		ActiveMqJmxConfigurationQuery query = new ActiveMqJmxConfigurationQuery();
		query.setStatus(ActiveMqConfigStatus.RUNNING);
		List<ActiveMqConfiguration> configurations = this.mqService.findConfigrations(query);
		if (null == configurations || configurations.isEmpty()) {
			this.logger.info(" No broker need to monitor, break.");
			return;
		}
		Map<String, String> referenceData = new HashMap<String, String>();
		List<BrokerInfo> brokerInfos = new ArrayList<BrokerInfo>();
		for (ActiveMqConfiguration activeMqConfiguration : configurations) {
			ActiveMqJmxConfiguration jmxConfiguration = (ActiveMqJmxConfiguration)activeMqConfiguration;
			BrokerInfo brokerInfo = this.mqService.collectBrokerInfo(jmxConfiguration,true);
			brokerInfos.add(brokerInfo);
			referenceData.put("broker#" + brokerInfo.getId(), jmxConfiguration.getId());
			
			if (null != brokerInfo.getQueueInfos()) {
				Date tempRecordDate = null;
				for (BrokerQueueInfo queueInfo : brokerInfo.getQueueInfos()) {
					String key = jmxConfiguration.getId() + queueInfo.getQueueName();
					String cost = "";
					//获取前10次统计数据
					BrokerQueueInfoQuery queueInfoQuery = new BrokerQueueInfoQuery();
					queueInfoQuery.setPageNum(1);
					queueInfoQuery.setPageSize(5);
					queueInfoQuery.setConfigId(jmxConfiguration.getId());
					queueInfoQuery.setQueueName(queueInfo.getQueueName());
					List<BrokerQueueInfo> dbQueueInfos = this.mqService.getLastQueueInfos(queueInfoQuery);
					
					if (!dbQueueInfos.isEmpty()) {
						BrokerQueueInfo first = dbQueueInfos.get(0);
						int enququeInterval = queueInfo.getMessageEnqueuedNum().intValue() - first.getMessageEnqueuedNum().intValue();
						int timeInteval = (int)(System.currentTimeMillis() - (this.mqService.findById(first.getBrokerInfoId()).getLogDate().getTime()));
						if (timeInteval == 0) {
							timeInteval = 1;
						}
						int enqueuePerMinute = enququeInterval / (timeInteval / 1000 / 60);
						if (enqueuePerMinute <= 0) {
							cost = "<span>" + enqueuePerMinute + "未收到消息</span>";
						}else {
							cost = enqueuePerMinute + "收到消息" + enqueuePerMinute + "条";
						}
					}
					
					if (dbQueueInfos.size() >= 5) {
						List<Integer> averages = new ArrayList<Integer>();
						int tempDequeueNo = 0;
						BrokerInfo dbBrokerInfo = this.mqService.findById(dbQueueInfos.get(0).getBrokerInfoId());
						int minutesInterval = 1;
						if (null != tempRecordDate) {
							minutesInterval = DateUtil.intervalMinutes(tempRecordDate, dbBrokerInfo.getLogDate());
						}
						for (BrokerQueueInfo lastQueueInfo : dbQueueInfos) {
							if (tempDequeueNo == 0) {
								tempDequeueNo = lastQueueInfo.getMessageDequeuedNum().intValue();
							}else {
								int tempAverage = (tempDequeueNo + lastQueueInfo.getMessageDequeuedNum().intValue());
								averages.add(tempAverage / minutesInterval);
							}
						}
						tempRecordDate = dbBrokerInfo.getLogDate();
						
						int average = MathUtil.trimmeanAverage(averages);
						if (average == 0) {
							cost += "<span>历史平均值0条/分钟，请检查服务</span>";
						}else {
							cost += "预计需要 " + (queueInfo.getPendingMessageNum().intValue() / average) + " 分钟";
						}
					}else {
						cost += "数据太少，无法计算";
					}
					referenceData.put(key, cost);
				}
			}
		}
		
		MqMonitorVelocityVo velocityVo = this.convert2Model(brokerInfos, referenceData);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("data", velocityVo);
		PropertiesUtil pu = new PropertiesUtil("application.properties");
		this.threadPoolTaskExecutor.execute(MailSender.getInstance(mailSenderService, "ActiveMQ Monitor Report", model, pu.getProperty("monitor.activemq.mail.to").split(","), "velocity/activemq_monitor.vm"));
	}

	private MqMonitorVelocityVo convert2Model(List<BrokerInfo> brokerInfos,Map<String, String> referenceData) {
		MqMonitorVelocityVo vo = new MqMonitorVelocityVo();
		vo.setReportDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm"));
		List<MqMonitorBrokerInfoVelocityVo> brokerInfoVelocityVos = new ArrayList<MqMonitorBrokerInfoVelocityVo>();
		
		for (BrokerInfo brokerInfo : brokerInfos) {
			MqMonitorBrokerInfoVelocityVo brokerInfoVelocityVo = new MqMonitorBrokerInfoVelocityVo();
			brokerInfoVelocityVo.setBrokerId(brokerInfo.getBrokerId());
			brokerInfoVelocityVo.setBrokerName(brokerInfo.getBrokerName());
			brokerInfoVelocityVo.setBrokerVersion(brokerInfo.getBrokerVersion());
			brokerInfoVelocityVo.setUptime(ActiveMqUtil.getUptimeStr(brokerInfo.getUptime().longValue()));
			
			brokerInfoVelocityVo.setDequeueNum(brokerInfo.getTotalDequeue().intValue());
			brokerInfoVelocityVo.setEnqueueNum(brokerInfo.getTotalEnqueue().intValue());
			brokerInfoVelocityVo.setReamining(brokerInfo.getTotalEnqueue().intValue() - brokerInfo.getTotalDequeue().intValue());
			
			brokerInfoVelocityVo.setMemory(MathUtil.getMbyte(brokerInfo.getMemoryLimit().longValue()));
			brokerInfoVelocityVo.setMemoryUsagePercent(brokerInfo.getMemoryPercentUsage().intValue());
			brokerInfoVelocityVo.setHardDisk(MathUtil.getGbyte(brokerInfo.getStoreLimit().longValue()));
			brokerInfoVelocityVo.setHardDiskUsagePercent(brokerInfo.getStorePercentUsage().intValue());
			brokerInfoVelocityVo.setTempCache(MathUtil.getMbyte(brokerInfo.getTempLimit().longValue()));
			brokerInfoVelocityVo.setTempCacheUsagePercent(brokerInfo.getTempPercentUsage().intValue());
			
			List<MqMonitorBrokerQueueInfoVelocityVo> queueInfoVelocityVos = new ArrayList<MqMonitorBrokerQueueInfoVelocityVo>();
			for (BrokerQueueInfo queueInfo : brokerInfo.getQueueInfos()) {
				MqMonitorBrokerQueueInfoVelocityVo queueInfoVelocityVo = new MqMonitorBrokerQueueInfoVelocityVo();
				queueInfoVelocityVo.setQueueName(queueInfo.getQueueName());
				queueInfoVelocityVo.setDequeueNum(queueInfo.getMessageDequeuedNum().intValue());
				queueInfoVelocityVo.setEnqueueNum(queueInfo.getMessageEnqueuedNum().intValue());
				queueInfoVelocityVo.setReamining(queueInfo.getMessageDequeuedNum().intValue() - queueInfo.getMessageEnqueuedNum().intValue());
				
				String configId = referenceData.get("broker#" + brokerInfo.getId());
				String strategy = referenceData.get(configId + queueInfo.getQueueName());
				queueInfoVelocityVo.setStrategy(strategy);
				queueInfoVelocityVos.add(queueInfoVelocityVo);
			}
			
			brokerInfoVelocityVo.setQueueInfos(queueInfoVelocityVos);
			brokerInfoVelocityVos.add(brokerInfoVelocityVo);
		}
		
		vo.setBrokerInfos(brokerInfoVelocityVos);
		return vo;
	}
}
