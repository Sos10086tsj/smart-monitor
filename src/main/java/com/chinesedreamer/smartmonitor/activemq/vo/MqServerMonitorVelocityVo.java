package com.chinesedreamer.smartmonitor.activemq.vo;
/**
 * Description:
 * Auth:Paris
 * Date:Feb 6, 2017
**/
public class MqServerMonitorVelocityVo {
	private String brokerId;
	private String content;
	private Integer warning;
	public String getBrokerId() {
		return brokerId;
	}
	public String getContent() {
		return content;
	}
	public Integer getWarning() {
		return warning;
	}
	public void setBrokerId(String brokerId) {
		this.brokerId = brokerId;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setWarning(Integer warning) {
		this.warning = warning;
	}
	
}
