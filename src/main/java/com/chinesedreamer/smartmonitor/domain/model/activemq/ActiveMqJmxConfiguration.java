package com.chinesedreamer.smartmonitor.domain.model.activemq;
/**
 * Description:
 * Auth:Paris
 * Date:Jan 20, 2017
**/
public class ActiveMqJmxConfiguration extends ActiveMqConfiguration{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5152872254222070239L;
	
	private String hostIp;//host or ip
	private String port;//default 1099
	private String appName;//default jmxrmi
	private String jmxDomain;//default org.apache.activemq
	private String brokerName;
	public String getHostIp() {
		return hostIp;
	}
	public String getPort() {
		return port;
	}
	public String getAppName() {
		return appName;
	}
	public String getJmxDomain() {
		return jmxDomain;
	}
	public String getBrokerName() {
		return brokerName;
	}
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public void setJmxDomain(String jmxDomain) {
		this.jmxDomain = jmxDomain;
	}
	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}
	
	
}
