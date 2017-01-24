package com.chinesedreamer.smartmonitor.activemq.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.chinesedreamer.smartmonitor.activemq.service.ActiveMqService;
import com.chinesedreamer.smartmonitor.domain.dao.activemq.ActiveMqJmxConfigurationDao;
import com.chinesedreamer.smartmonitor.domain.dao.activemq.BrokerInfoDao;
import com.chinesedreamer.smartmonitor.domain.dao.activemq.BrokerQueueInfoDao;
import com.chinesedreamer.smartmonitor.domain.model.activemq.ActiveMqConfiguration;
import com.chinesedreamer.smartmonitor.domain.model.activemq.ActiveMqJmxConfiguration;
import com.chinesedreamer.smartmonitor.domain.model.activemq.BrokerInfo;
import com.chinesedreamer.smartmonitor.domain.model.activemq.BrokerQueueInfo;
import com.chinesedreamer.smartmonitor.domain.query.activemq.ActiveMqJmxConfigurationQuery;
import com.chinesedreamer.smartmonitor.domain.query.activemq.BrokerQueueInfoQuery;
import com.chinesedreamer.smartmonitor.util.ActiveMqUtil;
import com.github.pagehelper.PageHelper;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 20, 2017
**/
@Service("jmxActiveMqMonitorService")
public class ActiveMqServiceJmxImpl implements ActiveMqService{
	
	private Logger logger = LoggerFactory.getLogger(ActiveMqServiceJmxImpl.class);

	
	@Resource
	private BrokerInfoDao brokerInfoDao;
	@Resource
	private BrokerQueueInfoDao brokerQueueInfoDao;
	@Resource
	private ActiveMqJmxConfigurationDao activeMqJmxConfigurationDao;
	
	@Override
	public BrokerInfo collectBrokerInfo(ActiveMqConfiguration configuration, boolean calculateMessageDate) {
		ActiveMqJmxConfiguration jmxConfiguration = (ActiveMqJmxConfiguration)configuration;
		
		try {
			JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + jmxConfiguration.getHostIp() + ":" + jmxConfiguration.getPort() +"/" +jmxConfiguration.getAppName());
			JMXConnector connector = JMXConnectorFactory.connect(url);
			connector.connect();
			MBeanServerConnection connection = connector.getMBeanServerConnection();
			ObjectName name = new ObjectName(jmxConfiguration.getJmxDomain() + ":brokerName=" + jmxConfiguration.getBrokerName() + ",type=Broker");
			BrokerViewMBean mBean = MBeanServerInvocationHandler.newProxyInstance(connection, name, BrokerViewMBean.class, true);
			
			BrokerInfo brokerInfo = this.convert2BrokerInfo(mBean,configuration);
			List<BrokerQueueInfo> queueInfos = new ArrayList<BrokerQueueInfo>();
			
			for (ObjectName queueName : mBean.getQueues()) {
				QueueViewMBean queueMBean = MBeanServerInvocationHandler.newProxyInstance(connection, queueName, QueueViewMBean.class, true);
				queueInfos.add(this.convert2BrokerQueueInfo(queueMBean,brokerInfo, calculateMessageDate));
			}
			brokerInfo.setQueueInfos(queueInfos);
			connector.close();

			return brokerInfo;
		} catch (Exception e) {
			this.logger.error("{}",e);
			return null;
		}
	}

	/**
	 * broker MBean转换成 BrokerInfo
	 * @param mBean
	 * @return
	 */
	private BrokerInfo convert2BrokerInfo(BrokerViewMBean mBean,ActiveMqConfiguration configuration) {
		BrokerInfo brokerInfo = new BrokerInfo();
		brokerInfo.setBrokerId(mBean.getBrokerId());
		brokerInfo.setBrokerName(mBean.getBrokerName());
		brokerInfo.setBrokerVersion(mBean.getBrokerVersion());
		brokerInfo.setUptime(new BigDecimal(ActiveMqUtil.getUptime(mBean.getUptime())));
		brokerInfo.setTotalEnqueue(new BigDecimal(mBean.getTotalEnqueueCount()));
		brokerInfo.setTotalDequeue(new BigDecimal(mBean.getTotalDequeueCount()));
		brokerInfo.setTotalProducer(new BigDecimal(mBean.getTotalProducerCount()));
		brokerInfo.setTotalConsumer(new BigDecimal(mBean.getTotalConsumerCount()));
		brokerInfo.setTotalMessage(new BigDecimal(mBean.getTotalMessageCount()));
		brokerInfo.setMemoryLimit(new BigDecimal(mBean.getMemoryLimit()));
		brokerInfo.setMemoryPercentUsage(new BigDecimal(mBean.getMemoryPercentUsage()));
		brokerInfo.setStoreLimit(new BigDecimal(mBean.getStoreLimit()));
		brokerInfo.setStorePercentUsage(new BigDecimal(mBean.getStorePercentUsage()));
		brokerInfo.setTempLimit(new BigDecimal(mBean.getTempLimit()));
		brokerInfo.setTempPercentUsage(new BigDecimal(mBean.getTempPercentUsage()));
		brokerInfo.setConfigId(configuration.getId());
		return brokerInfo;
	}
	
	/**
	 * QueueViewMBean 转换成 BrokerQueueInfo 
	 * @param queueMBean
	 * @return
	 */
	private BrokerQueueInfo convert2BrokerQueueInfo(QueueViewMBean queueMBean,BrokerInfo brokerInfo, boolean calculateMessageDate) {
		BrokerQueueInfo queueInfo = new BrokerQueueInfo();
		queueInfo.setConsumerNum(new BigDecimal(queueMBean.getConsumerCount()));
		queueInfo.setMessageDequeuedNum(new BigDecimal(queueMBean.getDequeueCount()));
		queueInfo.setMessageEnqueuedNum(new BigDecimal(queueMBean.getEnqueueCount()));
		queueInfo.setPendingMessageNum(new BigDecimal(queueMBean.getQueueSize()));
		queueInfo.setQueueName(queueMBean.getName());
		queueInfo.setBrokerInfoId(brokerInfo.getId());
		if (calculateMessageDate) {
			try {
				CompositeData[] messages = queueMBean.browse();
				if (null != messages && messages.length > 0) {
					Date earliestMessageReceiveDate = null;
					Date lastMessageReceiveDate = null;
					for (CompositeData message : messages) {
						Date messageReceiveDate = (Date)message.get("JMSTimestamp");
						if (null != messageReceiveDate) {
							if (null == earliestMessageReceiveDate) {
								earliestMessageReceiveDate = messageReceiveDate;
							}
							if (null == lastMessageReceiveDate) {
								lastMessageReceiveDate = messageReceiveDate;
							}
							if (messageReceiveDate.before(earliestMessageReceiveDate)) {
								earliestMessageReceiveDate = messageReceiveDate;
							}
							if (messageReceiveDate.after(lastMessageReceiveDate)) {
								lastMessageReceiveDate = messageReceiveDate;
							}
						}
					}
					queueInfo.setEarliestMessageReceiveDate(earliestMessageReceiveDate);
					queueInfo.setLastMessageReceiveDate(earliestMessageReceiveDate);
				}
			} catch (OpenDataException e) {
				this.logger.error("{}",e);
			}
		}
		return queueInfo;
	}

	@Override
	public List<ActiveMqConfiguration> findConfigrations(ActiveMqJmxConfigurationQuery query) {
		List<ActiveMqJmxConfiguration> jmxConfigurations = this.activeMqJmxConfigurationDao.findAll(query);
		List<ActiveMqConfiguration> configurations = new ArrayList<ActiveMqConfiguration>();
		for (ActiveMqJmxConfiguration jmxConfiguration : jmxConfigurations) {
			configurations.add((ActiveMqConfiguration)jmxConfiguration);
		}
		return configurations;
	}

	@Override
	public void saveBrokerInfo(BrokerInfo brokerInfo) {
		this.brokerInfoDao.save(brokerInfo);
		if (null != brokerInfo.getQueueInfos() && !brokerInfo.getQueueInfos().isEmpty()) {
			this.brokerQueueInfoDao.saveInBatch(brokerInfo.getQueueInfos());
		}
	}

	@Override
	public List<BrokerQueueInfo> getLastQueueInfos(BrokerQueueInfoQuery queueInfoQuery) {
		PageHelper.startPage(queueInfoQuery.getPageNum(), queueInfoQuery.getPageSize());
		return this.brokerQueueInfoDao.getLatestList(queueInfoQuery.getConfigId(),queueInfoQuery.getQueueName());
	}

	@Override
	public BrokerInfo findById(String id) {
		return this.brokerInfoDao.findById(id);
	}
}
