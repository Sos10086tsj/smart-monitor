package com.chinesedreamer.smartmonitor.activemq.vo;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 24, 2017
**/
public class MqMonitorBrokerQueueInfoVelocityVo {
	private String queueName;
	
	private Integer dequeueNum;
	private Integer enqueueNum;
	private Integer reamining;
	
	private String strategy;

	public String getQueueName() {
		return queueName;
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

	public String getStrategy() {
		return strategy;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
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

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	
	
}
