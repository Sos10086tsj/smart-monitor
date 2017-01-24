package com.chinesedreamer.smartmonitor.activemq.vo;

import java.util.List;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 24, 2017
**/
public class MqMonitorVelocityVo {
	private String reportDate;
	private List<MqMonitorBrokerInfoVelocityVo> brokerInfos;
	public String getReportDate() {
		return reportDate;
	}
	public List<MqMonitorBrokerInfoVelocityVo> getBrokerInfos() {
		return brokerInfos;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public void setBrokerInfos(List<MqMonitorBrokerInfoVelocityVo> brokerInfos) {
		this.brokerInfos = brokerInfos;
	}
	
	
}
