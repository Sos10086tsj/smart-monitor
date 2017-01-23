package com.chinesedreamer.smartmonitor.domain.model.quartz;

import java.util.Date;

import com.chinesedreamer.smartmonitor.domain.model.BaseModel;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 22, 2017
**/
public class QuartzJob extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -722897145921938545L;

	private String groupCode;
	private String name;
	private String beanName;
	private String executeMethod;
	private String cronExpression;
	private Boolean needRefresh = Boolean.FALSE;
	private Boolean concurrent = Boolean.FALSE;
	private Date startTime;
	private Date endTime;
	private Boolean startNow = Boolean.FALSE;
	
	
	public Boolean getStartNow() {
		return startNow;
	}
	public void setStartNow(Boolean startNow) {
		this.startNow = startNow;
	}
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Boolean getConcurrent() {
		return concurrent;
	}
	public void setConcurrent(Boolean concurrent) {
		this.concurrent = concurrent;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public String getName() {
		return name;
	}
	public String getBeanName() {
		return beanName;
	}
	public String getExecuteMethod() {
		return executeMethod;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public Boolean getNeedRefresh() {
		return needRefresh;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public void setExecuteMethod(String executeMethod) {
		this.executeMethod = executeMethod;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public void setNeedRefresh(Boolean needRefresh) {
		this.needRefresh = needRefresh;
	}
	@Override
	public String toString() {
		return "QuartzJob [groupCode=" + groupCode + ", name=" + name + ", beanName=" + beanName + ", executeMethod="
				+ executeMethod + ", cronExpression=" + cronExpression + ", needRefresh=" + needRefresh
				+ ", concurrent=" + concurrent + ", startTime=" + startTime + ", endTime=" + endTime + ", startNow="
				+ startNow + ", id=" + id + "]";
	}
	
	
}
