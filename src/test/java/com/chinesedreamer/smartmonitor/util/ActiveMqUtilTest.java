package com.chinesedreamer.smartmonitor.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 20, 2017
**/
public class ActiveMqUtilTest {

	@Test
	public void testGetUptime() {
		//1  30s
		String str = "30" + " seconds";
		long time = ActiveMqUtil.getUptime(str);
		assertEquals(30l, time);
		
		//2. 1 分 30s
		str = 1 + " minute " + 30 + " seconds";
		time = ActiveMqUtil.getUptime(str);
		assertEquals(90l, time);
		
		//3. 2分10s
		str = 2 + " minutes " + 10 + " seconds";
		time = ActiveMqUtil.getUptime(str);
		assertEquals(130l, time);
		
	}

}
