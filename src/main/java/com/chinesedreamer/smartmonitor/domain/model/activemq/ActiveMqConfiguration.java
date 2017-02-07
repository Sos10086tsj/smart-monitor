package com.chinesedreamer.smartmonitor.domain.model.activemq;

import com.chinesedreamer.smartmonitor.domain.model.BaseModel;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 20, 2017
**/
public abstract class ActiveMqConfiguration extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5152672001865046229L;
	
	protected String mqPort;
	protected String mqUsername;
	protected String mqPassword;
	public String getMqPort() {
		return mqPort;
	}
	public String getMqUsername() {
		return mqUsername;
	}
	public String getMqPassword() {
		return mqPassword;
	}
	public void setMqPort(String mqPort) {
		this.mqPort = mqPort;
	}
	public void setMqUsername(String mqUsername) {
		this.mqUsername = mqUsername;
	}
	public void setMqPassword(String mqPassword) {
		this.mqPassword = mqPassword;
	}
	
	
}
