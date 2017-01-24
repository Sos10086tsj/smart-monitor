package com.chinesedreamer.smartmonitor.domain.dao.activemq;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinesedreamer.smartmonitor.domain.model.activemq.BrokerQueueInfo;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 22, 2017
**/
public interface BrokerQueueInfoDao {
	/**
	 * 批量保存
	 * @param queueInfos
	 * @return
	 */
	public int saveInBatch(List<BrokerQueueInfo> queueInfos);
	
	/**
	 * 获取最新列表
	 * @param infoId
	 * @param queueName
	 * @return
	 */
	public List<BrokerQueueInfo> getLatestList(@Param(value="configId")String configId,@Param(value="queueName")String queueName);
}
