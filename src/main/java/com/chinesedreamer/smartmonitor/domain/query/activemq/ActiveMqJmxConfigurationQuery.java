package com.chinesedreamer.smartmonitor.domain.query.activemq;

import com.chinesedreamer.smartmonitor.domain.constant.activemq.ActiveMqConfigStatus;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 22, 2017
**/
public class ActiveMqJmxConfigurationQuery {
	private ActiveMqConfigStatus status;

	public ActiveMqConfigStatus getStatus() {
		return status;
	}

	public void setStatus(ActiveMqConfigStatus status) {
		this.status = status;
	}
	
	
}
