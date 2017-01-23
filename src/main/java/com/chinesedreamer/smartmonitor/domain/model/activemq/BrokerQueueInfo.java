package com.chinesedreamer.smartmonitor.domain.model.activemq;

import java.math.BigDecimal;
import java.util.Date;

import com.chinesedreamer.smartmonitor.domain.model.BaseModel;

/**
 * Description: queue总体统计
 * Auth:Paris
 * Date:Jan 22, 2017
**/
public class BrokerQueueInfo extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8170274752908166162L;
	
	private String brokerInfoId;// {@link com.chinesedreamer.smartmonitor.domain.model.activemq.BrokerInfo} id外键
	private String queueName;
	private BigDecimal pendingMessageNum;
	private BigDecimal consumerNum;
	private BigDecimal messageEnqueuedNum;
	private BigDecimal messageDequeuedNum;
	
	private Date earliestMessageReceiveDate;//最早的消息
	private Date lastMessageReceiveDate;//最新消息
	
	public Date getEarliestMessageReceiveDate() {
		return earliestMessageReceiveDate;
	}
	public Date getLastMessageReceiveDate() {
		return lastMessageReceiveDate;
	}
	public void setEarliestMessageReceiveDate(Date earliestMessageReceiveDate) {
		this.earliestMessageReceiveDate = earliestMessageReceiveDate;
	}
	public void setLastMessageReceiveDate(Date lastMessageReceiveDate) {
		this.lastMessageReceiveDate = lastMessageReceiveDate;
	}
	public String getBrokerInfoId() {
		return brokerInfoId;
	}
	public String getQueueName() {
		return queueName;
	}
	public BigDecimal getPendingMessageNum() {
		return pendingMessageNum;
	}
	public BigDecimal getConsumerNum() {
		return consumerNum;
	}
	public BigDecimal getMessageEnqueuedNum() {
		return messageEnqueuedNum;
	}
	public BigDecimal getMessageDequeuedNum() {
		return messageDequeuedNum;
	}
	public void setBrokerInfoId(String brokerInfoId) {
		this.brokerInfoId = brokerInfoId;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public void setPendingMessageNum(BigDecimal pendingMessageNum) {
		this.pendingMessageNum = pendingMessageNum;
	}
	public void setConsumerNum(BigDecimal consumerNum) {
		this.consumerNum = consumerNum;
	}
	public void setMessageEnqueuedNum(BigDecimal messageEnqueuedNum) {
		this.messageEnqueuedNum = messageEnqueuedNum;
	}
	public void setMessageDequeuedNum(BigDecimal messageDequeuedNum) {
		this.messageDequeuedNum = messageDequeuedNum;
	}
	
	
}
