package com.chinesedreamer.smartmonitor.tools.mail.model;

import java.io.Serializable;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 23, 2017
**/
public class MailConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4478069540871022112L;

	private String host;
	private String username;
	private String password;
	private String from;
	private String env;
	
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	public String getHost() {
		return host;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
	
	
}
