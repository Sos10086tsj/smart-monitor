package com.chinesedreamer.smartmonitor.domain.query.activemq;

import com.chinesedreamer.smartmonitor.domain.query.BaseQuery;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 23, 2017
**/
public class BrokerQueueInfoQuery extends BaseQuery{
	private String infoId;
	private String queueName;
	public String getInfoId() {
		return infoId;
	}
	public String getQueueName() {
		return queueName;
	}
	public void setInfoId(String infoId) {
		this.infoId = infoId;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
}
