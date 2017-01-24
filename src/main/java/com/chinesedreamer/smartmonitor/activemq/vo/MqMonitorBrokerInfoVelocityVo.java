package com.chinesedreamer.smartmonitor.activemq.vo;

import java.util.List;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 24, 2017
**/
public class MqMonitorBrokerInfoVelocityVo {
	private String brokerId;
	private String brokerName;
	private String brokerVersion;
	private String uptime;
	
	private Integer dequeueNum;
	private Integer enqueueNum;
	private Integer reamining;
	
	private Integer memory;
	private Integer memoryUsagePercent;
	private Integer hardDisk;
	private Integer hardDiskUsagePercent;
	private Integer tempCache;
	private Integer tempCacheUsagePercent;
	
	private List<MqMonitorBrokerQueueInfoVelocityVo> queueInfos;
	
	
	public List<MqMonitorBrokerQueueInfoVelocityVo> getQueueInfos() {
		return queueInfos;
	}
	public void setQueueInfos(List<MqMonitorBrokerQueueInfoVelocityVo> queueInfos) {
		this.queueInfos = queueInfos;
	}
	public String getBrokerId() {
		return brokerId;
	}
	public String getBrokerName() {
		return brokerName;
	}
	public String getBrokerVersion() {
		return brokerVersion;
	}
	public String getUptime() {
		return uptime;
	}
	public Integer getDequeueNum() {
		return dequeueNum;
	}
	public Integer getEnqueueNum() {
		return enqueueNum;
	}
	public Integer getReamining() {
		return reamining;
	}
	public Integer getMemory() {
		return memory;
	}
	public Integer getMemoryUsagePercent() {
		return memoryUsagePercent;
	}
	public Integer getHardDisk() {
		return hardDisk;
	}
	public Integer getHardDiskUsagePercent() {
		return hardDiskUsagePercent;
	}
	public Integer getTempCache() {
		return tempCache;
	}
	public Integer getTempCacheUsagePercent() {
		return tempCacheUsagePercent;
	}
	public void setBrokerId(String brokerId) {
		this.brokerId = brokerId;
	}
	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}
	public void setBrokerVersion(String brokerVersion) {
		this.brokerVersion = brokerVersion;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	public void setDequeueNum(Integer dequeueNum) {
		this.dequeueNum = dequeueNum;
	}
	public void setEnqueueNum(Integer enqueueNum) {
		this.enqueueNum = enqueueNum;
	}
	public void setReamining(Integer reamining) {
		this.reamining = reamining;
	}
	public void setMemory(Integer memory) {
		this.memory = memory;
	}
	public void setMemoryUsagePercent(Integer memoryUsagePercent) {
		this.memoryUsagePercent = memoryUsagePercent;
	}
	public void setHardDisk(Integer hardDisk) {
		this.hardDisk = hardDisk;
	}
	public void setHardDiskUsagePercent(Integer hardDiskUsagePercent) {
		this.hardDiskUsagePercent = hardDiskUsagePercent;
	}
	public void setTempCache(Integer tempCache) {
		this.tempCache = tempCache;
	}
	public void setTempCacheUsagePercent(Integer tempCacheUsagePercent) {
		this.tempCacheUsagePercent = tempCacheUsagePercent;
	}
	
	
}
