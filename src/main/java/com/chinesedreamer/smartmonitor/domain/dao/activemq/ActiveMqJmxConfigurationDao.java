package com.chinesedreamer.smartmonitor.domain.dao.activemq;

import java.util.List;

import com.chinesedreamer.smartmonitor.domain.model.activemq.ActiveMqJmxConfiguration;
import com.chinesedreamer.smartmonitor.domain.query.activemq.ActiveMqJmxConfigurationQuery;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 22, 2017
**/
public interface ActiveMqJmxConfigurationDao {
	public List<ActiveMqJmxConfiguration> findAll(ActiveMqJmxConfigurationQuery query);
}
