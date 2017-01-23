package com.chinesedreamer.smartmonitor.activemq.service;

import java.util.List;

import com.chinesedreamer.smartmonitor.domain.model.activemq.ActiveMqConfiguration;
import com.chinesedreamer.smartmonitor.domain.model.activemq.BrokerInfo;
import com.chinesedreamer.smartmonitor.domain.model.activemq.BrokerQueueInfo;
import com.chinesedreamer.smartmonitor.domain.query.activemq.ActiveMqJmxConfigurationQuery;
import com.chinesedreamer.smartmonitor.domain.query.activemq.BrokerQueueInfoQuery;

/**
 * Description: 监控activemq 状态
 * Auth:Paris
 * Date:Jan 20, 2017
**/
public interface ActiveMqService {
	
	public void saveBrokerInfo(BrokerInfo brokerInfo);
	
	/**
	 * 读取并保存activemq broker的状况
	 * @param configuration
	 * @param calculateMessageDate  是否统计最早/最晚时间
	 */
	public BrokerInfo collectBrokerInfo(ActiveMqConfiguration configuration, boolean calculateMessageDate);
	
	/**
	 * 获取配置列表
	 * @param query
	 * @return
	 */
	public List<ActiveMqConfiguration> findConfigrations(ActiveMqJmxConfigurationQuery query);
	
	public List<BrokerQueueInfo> getLastQueueInfos(BrokerQueueInfoQuery queueInfoQuery);
	
	public BrokerInfo findById(String id);
}
