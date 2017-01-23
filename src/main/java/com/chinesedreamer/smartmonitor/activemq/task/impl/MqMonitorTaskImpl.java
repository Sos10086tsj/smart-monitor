package com.chinesedreamer.smartmonitor.activemq.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.chinesedreamer.smartmonitor.activemq.service.ActiveMqService;
import com.chinesedreamer.smartmonitor.activemq.task.MqMonitorTask;
import com.chinesedreamer.smartmonitor.domain.constant.activemq.ActiveMqConfigStatus;
import com.chinesedreamer.smartmonitor.domain.model.activemq.ActiveMqConfiguration;
import com.chinesedreamer.smartmonitor.domain.model.activemq.ActiveMqJmxConfiguration;
import com.chinesedreamer.smartmonitor.domain.model.activemq.BrokerInfo;
import com.chinesedreamer.smartmonitor.domain.model.activemq.BrokerQueueInfo;
import com.chinesedreamer.smartmonitor.domain.query.activemq.ActiveMqJmxConfigurationQuery;
import com.chinesedreamer.smartmonitor.domain.query.activemq.BrokerQueueInfoQuery;
import com.chinesedreamer.smartmonitor.util.DateUtil;
import com.chinesedreamer.smartmonitor.util.MathUtil;

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

	@Override
	public void monitor() {
		this.logger.info(" Start to monitor MQ task.");
		ActiveMqJmxConfigurationQuery query = new ActiveMqJmxConfigurationQuery();
		query.setStatus(ActiveMqConfigStatus.RUNNING);
		List<ActiveMqConfiguration> configurations = this.mqService.findConfigrations(query);
		if (null == configurations || configurations.isEmpty()) {
			this.logger.info(" No broker need to monitor, break.");
			return;
		}
		Map<String, Object> referenceData = new HashMap<String, Object>();
		List<BrokerInfo> brokerInfos = new ArrayList<BrokerInfo>();
		for (ActiveMqConfiguration activeMqConfiguration : configurations) {
			ActiveMqJmxConfiguration jmxConfiguration = (ActiveMqJmxConfiguration)activeMqConfiguration;
			brokerInfos.add(this.mqService.collectBrokerInfo(jmxConfiguration,true));
		}
		//计算预计完成时间
		for (BrokerInfo brokerInfo : brokerInfos) {
			if (null != brokerInfo.getQueueInfos()) {
				Date tempRecordDate = null;
				for (BrokerQueueInfo queueInfo : brokerInfo.getQueueInfos()) {
					String key = queueInfo.getBrokerInfoId() + queueInfo.getQueueName();
					String cost = "";
					//获取前10次统计数据
					BrokerQueueInfoQuery queueInfoQuery = new BrokerQueueInfoQuery();
					queueInfoQuery.setPageNum(1);
					queueInfoQuery.setPageSize(5);
					queueInfoQuery.setInfoId(queueInfo.getBrokerInfoId());
					queueInfoQuery.setQueueName(queueInfo.getQueueName());
					List<BrokerQueueInfo> dbQueueInfos = this.mqService.getLastQueueInfos(queueInfoQuery);
					if (dbQueueInfos.size() >= 5) {
						List<Integer> averages = new ArrayList<Integer>();
						int tempDequeueNo = 0;
						BrokerInfo dbBrokerInfo = this.mqService.findById(queueInfo.getBrokerInfoId());
						int minutesInterval = DateUtil.intervalMinutes(tempRecordDate, dbBrokerInfo.getLogDate());
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
							cost = "历史平均值0条/分钟，请检查服务";
						}else {
							cost = "预计需要 " + (queueInfo.getPendingMessageNum().intValue() / average) + " 分钟";
						}
					}else {
						cost = "数据太少，无法计算";
					}
					referenceData.put(key, cost);
				}
			}
		}
	}

}
