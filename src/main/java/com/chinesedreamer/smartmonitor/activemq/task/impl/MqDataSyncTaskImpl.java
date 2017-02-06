package com.chinesedreamer.smartmonitor.activemq.task.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.chinesedreamer.smartmonitor.activemq.service.ActiveMqService;
import com.chinesedreamer.smartmonitor.activemq.task.MqDataSyncTask;
import com.chinesedreamer.smartmonitor.domain.constant.activemq.ActiveMqConfigStatus;
import com.chinesedreamer.smartmonitor.domain.model.activemq.ActiveMqConfiguration;
import com.chinesedreamer.smartmonitor.domain.model.activemq.ActiveMqJmxConfiguration;
import com.chinesedreamer.smartmonitor.domain.model.activemq.BrokerInfo;
import com.chinesedreamer.smartmonitor.domain.query.activemq.ActiveMqJmxConfigurationQuery;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 22, 2017
**/
@Service("mqDataSyncTask")
public class MqDataSyncTaskImpl implements MqDataSyncTask{
	private Logger logger = LoggerFactory.getLogger(MqDataSyncTaskImpl.class);

	@Resource
	private ActiveMqService activeMqService;
	
	/* (non-Javadoc)
	 * 15分钟统计一次
	 * @see com.chinesedreamer.smartmonitor.activemq.task.MqDataSyncTask#updateMqData()
	 */
	@Override
	public void updateMqData() {
		this.logger.info(" Start to update MQ broker info.");
		ActiveMqJmxConfigurationQuery query = new ActiveMqJmxConfigurationQuery();
		query.setStatus(ActiveMqConfigStatus.RUNNING);
		List<ActiveMqConfiguration> configurations = this.activeMqService.findConfigrations(query);
		if (null == configurations || configurations.isEmpty()) {
			this.logger.info(" No broker need to sync, break.");
			return;
		}
		for (ActiveMqConfiguration activeMqConfiguration : configurations) {
			ActiveMqJmxConfiguration jmxConfiguration = (ActiveMqJmxConfiguration)activeMqConfiguration;
			BrokerInfo brokerInfo = this.activeMqService.collectBrokerInfo(jmxConfiguration,false);
			this.activeMqService.saveBrokerInfo(brokerInfo);
		}
		this.logger.info(" MQ broker info updated.");
	}

}
