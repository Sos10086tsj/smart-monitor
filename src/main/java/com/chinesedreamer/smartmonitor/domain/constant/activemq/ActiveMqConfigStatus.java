package com.chinesedreamer.smartmonitor.domain.constant.activemq;
/**
 * Description:
 * Auth:Paris
 * Date:Jan 22, 2017
**/
public enum ActiveMqConfigStatus {
	RUNNING(0,"运行中"),
	STOPPED(1,"已停止");
	
	private final Integer status;
	private final String desc;
	private ActiveMqConfigStatus(Integer status,String desc){
		this.status = status;
		this.desc = desc;
	}
	public Integer getStatus() {
		return status;
	}
	public String getDesc() {
		return desc;
	}
	public static ActiveMqConfigStatus get(Integer status) {
		for (ActiveMqConfigStatus configStatus : ActiveMqConfigStatus.values()) {
			if (configStatus.getStatus().intValue() == status.intValue()) {
				return configStatus;
			}
		}
		return null;
	}
}
