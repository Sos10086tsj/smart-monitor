package com.chinesedreamer.smartmonitor.activemq.service.impl;

import javax.annotation.Resource;

import org.junit.Test;

import com.chinesedreamer.smartmonitor.BaseTest;
import com.chinesedreamer.smartmonitor.activemq.service.ActiveMqService;
import com.chinesedreamer.smartmonitor.domain.model.activemq.ActiveMqJmxConfiguration;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 22, 2017
**/
public class ActiveMqMonitorServiceJmxImplTest extends BaseTest{

	@Resource(name="jmxActiveMqMonitorService")
	private ActiveMqService jmxActiveMqMonitorService;
	@Test
	public void testSaveBrokerInfo() {
		ActiveMqJmxConfiguration jmxConfiguration = new ActiveMqJmxConfiguration();
		jmxConfiguration.setAppName("jmxrmi");
		jmxConfiguration.setBrokerName("localhost");
		jmxConfiguration.setHostIp("localhost");
		jmxConfiguration.setId("test#" + System.currentTimeMillis());
		jmxConfiguration.setJmxDomain("org.apache.activemq");
		jmxConfiguration.setPort("1099");
		this.jmxActiveMqMonitorService.collectBrokerInfo(jmxConfiguration,false);
	}

}
