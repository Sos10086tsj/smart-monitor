package com.chinesedreamer.smartmonitor.domain.dao.activemq;

import com.chinesedreamer.smartmonitor.domain.model.activemq.BrokerInfo;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 22, 2017
**/
public interface BrokerInfoDao {
	public int save(BrokerInfo brokerInfo);
	
	public BrokerInfo findById(String id);
}
