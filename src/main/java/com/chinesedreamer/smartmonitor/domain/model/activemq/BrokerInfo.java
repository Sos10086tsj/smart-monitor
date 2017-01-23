package com.chinesedreamer.smartmonitor.domain.model.activemq;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.chinesedreamer.smartmonitor.domain.model.BaseModel;

/**
 * Description:	TABLE M_ACTIVEMQ_BROKER
 * Auth:Paris
 * Date:Jan 20, 2017
**/
public class BrokerInfo extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5241836646187804327L;

	private String configId; //{@linke com.chinesedreamer.smartmonitor.domain.model.activemq.ActiveMqJxmConfiguration} id外键
	
	private String brokerId;
	private String brokerName;
	private String brokerVersion;
	private BigDecimal uptime;
	private BigDecimal totalEnqueue;//总入队消息数
	private BigDecimal totalDequeue;//总出队消息数
	private BigDecimal totalProducer;//总生产者
	private BigDecimal totalConsumer;//总消费者
	private BigDecimal totalMessage;//unacknowledged 总数
	private BigDecimal memoryLimit;//最大内存
	private BigDecimal memoryPercentUsage;//内存使用率
	private BigDecimal storeLimit;//持久化硬盘存储空间
	private BigDecimal storePercentUsage;//硬盘使用率
	private BigDecimal tempLimit;//临时缓存区大小
	private BigDecimal tempPercentUsage;//临时缓存区使用率
	
	private Date logDate = new Date();//日志记录时间
	
	private List<BrokerQueueInfo> queueInfos;//queue 列表

	public List<BrokerQueueInfo> getQueueInfos() {
		return queueInfos;
	}
	public void setQueueInfos(List<BrokerQueueInfo> queueInfos) {
		this.queueInfos = queueInfos;
	}
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
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
	public BigDecimal getUptime() {
		return uptime;
	}
	public BigDecimal getTotalEnqueue() {
		return totalEnqueue;
	}
	public BigDecimal getTotalDequeue() {
		return totalDequeue;
	}
	public BigDecimal getTotalProducer() {
		return totalProducer;
	}
	public BigDecimal getTotalConsumer() {
		return totalConsumer;
	}
	public BigDecimal getTotalMessage() {
		return totalMessage;
	}
	public BigDecimal getMemoryLimit() {
		return memoryLimit;
	}
	public BigDecimal getMemoryPercentUsage() {
		return memoryPercentUsage;
	}
	public BigDecimal getStoreLimit() {
		return storeLimit;
	}
	public BigDecimal getStorePercentUsage() {
		return storePercentUsage;
	}
	public BigDecimal getTempLimit() {
		return tempLimit;
	}
	public BigDecimal getTempPercentUsage() {
		return tempPercentUsage;
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
	public void setUptime(BigDecimal uptime) {
		this.uptime = uptime;
	}
	public void setTotalEnqueue(BigDecimal totalEnqueue) {
		this.totalEnqueue = totalEnqueue;
	}
	public void setTotalDequeue(BigDecimal totalDequeue) {
		this.totalDequeue = totalDequeue;
	}
	public void setTotalProducer(BigDecimal totalProducer) {
		this.totalProducer = totalProducer;
	}
	public void setTotalConsumer(BigDecimal totalConsumer) {
		this.totalConsumer = totalConsumer;
	}
	public void setTotalMessage(BigDecimal totalMessage) {
		this.totalMessage = totalMessage;
	}
	public void setMemoryLimit(BigDecimal memoryLimit) {
		this.memoryLimit = memoryLimit;
	}
	public void setMemoryPercentUsage(BigDecimal memoryPercentUsage) {
		this.memoryPercentUsage = memoryPercentUsage;
	}
	public void setStoreLimit(BigDecimal storeLimit) {
		this.storeLimit = storeLimit;
	}
	public void setStorePercentUsage(BigDecimal storePercentUsage) {
		this.storePercentUsage = storePercentUsage;
	}
	public void setTempLimit(BigDecimal tempLimit) {
		this.tempLimit = tempLimit;
	}
	public void setTempPercentUsage(BigDecimal tempPercentUsage) {
		this.tempPercentUsage = tempPercentUsage;
	}
	
	
}
