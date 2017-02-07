package com.chinesedreamer.smartmonitor.activemq.task;
/**
 * Description:
 * Auth:Paris
 * Date:Feb 6, 2017
**/
public interface MqServerMonitorTask {
	/**
	 * 监控broker是否可以正常接收到消息
	 */
	public void monitorAccessStatus();
}
