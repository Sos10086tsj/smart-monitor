package com.chinesedreamer.smartmonitor.activemq.task.impl;

import javax.annotation.Resource;

import org.junit.Test;

import com.chinesedreamer.smartmonitor.BaseTest;
import com.chinesedreamer.smartmonitor.activemq.task.MqServerMonitorTask;

/**
 * Description:
 * Auth:Paris
 * Date:Feb 6, 2017
**/
public class MqServerMonitorTaskImplTest extends BaseTest{
	@Resource(name="mqServerMonitorTask")
	private MqServerMonitorTask mqServerMonitorTask;
	@Test
	public void testMonitorAccessStatus() {
		this.mqServerMonitorTask.monitorAccessStatus();
	}

}
