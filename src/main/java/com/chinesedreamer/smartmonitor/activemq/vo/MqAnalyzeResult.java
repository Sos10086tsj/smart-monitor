package com.chinesedreamer.smartmonitor.activemq.vo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.chinesedreamer.smartmonitor.domain.model.activemq.BrokerInfo;
import com.chinesedreamer.smartmonitor.domain.model.activemq.BrokerQueueInfo;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 23, 2017
**/
public class MqAnalyzeResult {
	private BrokerInfo borkerInfo;//broker信息
	private List<BrokerQueueInfo> queueInfos;//queue信息
	private Date lastMessageReceiveDate;//最后一条消息收到时间
	private List<Map<String, Object>> referData;//参考值
	public BrokerInfo getBorkerInfo() {
		return borkerInfo;
	}
	public List<BrokerQueueInfo> getQueueInfos() {
		return queueInfos;
	}
	public Date getLastMessageReceiveDate() {
		return lastMessageReceiveDate;
	}
	public List<Map<String, Object>> getReferData() {
		return referData;
	}
	public void setBorkerInfo(BrokerInfo borkerInfo) {
		this.borkerInfo = borkerInfo;
	}
	public void setQueueInfos(List<BrokerQueueInfo> queueInfos) {
		this.queueInfos = queueInfos;
	}
	public void setLastMessageReceiveDate(Date lastMessageReceiveDate) {
		this.lastMessageReceiveDate = lastMessageReceiveDate;
	}
	public void setReferData(List<Map<String, Object>> referData) {
		this.referData = referData;
	}
	
	
}
