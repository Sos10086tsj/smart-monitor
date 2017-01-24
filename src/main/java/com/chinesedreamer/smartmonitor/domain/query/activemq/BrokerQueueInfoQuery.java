package com.chinesedreamer.smartmonitor.domain.query.activemq;

import com.chinesedreamer.smartmonitor.domain.query.BaseQuery;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 23, 2017
**/
public class BrokerQueueInfoQuery extends BaseQuery{
	private String configId;
	private String queueName;
	public String getConfigId() {
		return configId;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
	
}
