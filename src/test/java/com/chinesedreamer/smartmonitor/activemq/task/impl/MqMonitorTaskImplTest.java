package com.chinesedreamer.smartmonitor.activemq.task.impl;

import javax.annotation.Resource;

import org.junit.Test;

import com.chinesedreamer.smartmonitor.BaseTest;
import com.chinesedreamer.smartmonitor.activemq.task.MqMonitorTask;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 23, 2017
**/
public class MqMonitorTaskImplTest extends BaseTest{
	
	@Resource
	private MqMonitorTask mqMonitorTask;

	@Test
	public void testMonitor() {
		this.mqMonitorTask.monitor();
	}

}
