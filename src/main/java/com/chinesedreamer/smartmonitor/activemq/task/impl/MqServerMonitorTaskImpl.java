package com.chinesedreamer.smartmonitor.activemq.task.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.chinesedreamer.smartmonitor.activemq.service.ActiveMqService;
import com.chinesedreamer.smartmonitor.activemq.task.MqServerMonitorTask;
import com.chinesedreamer.smartmonitor.activemq.vo.MqServerMonitorVelocityVo;
import com.chinesedreamer.smartmonitor.domain.constant.activemq.ActiveMqConfigStatus;
import com.chinesedreamer.smartmonitor.domain.model.activemq.ActiveMqConfiguration;
import com.chinesedreamer.smartmonitor.domain.model.activemq.ActiveMqJmxConfiguration;
import com.chinesedreamer.smartmonitor.domain.model.activemq.BrokerInfo;
import com.chinesedreamer.smartmonitor.domain.model.activemq.BrokerQueueInfo;
import com.chinesedreamer.smartmonitor.domain.query.activemq.ActiveMqJmxConfigurationQuery;
import com.chinesedreamer.smartmonitor.tools.mail.MailSender;
import com.chinesedreamer.smartmonitor.tools.mail.service.MailSenderService;
import com.chinesedreamer.smartmonitor.util.ActiveMqUtil;
import com.chinesedreamer.smartmonitor.util.PropertiesUtil;

/**
 * Description:
 * Auth:Paris
 * Date:Feb 6, 2017
**/
@Service("mqServerMonitorTask")
public class MqServerMonitorTaskImpl implements MqServerMonitorTask{
	private Logger logger = LoggerFactory.getLogger(MqServerMonitorTaskImpl.class);
	
	@Resource
	private MailSenderService mailSenderService;
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	@Resource
	private ActiveMqService mqService;

	@Override
	public void monitorAccessStatus() {
		this.logger.info(" Start to monitor MQ Serve status task...");
		ActiveMqJmxConfigurationQuery query = new ActiveMqJmxConfigurationQuery();
		query.setStatus(ActiveMqConfigStatus.RUNNING);
		List<ActiveMqConfiguration> configurations = this.mqService.findConfigrations(query);
		if (null == configurations || configurations.isEmpty()) {
			this.logger.info(" No broker need to monitor, break.");
			return;
		}
		
		List<MqServerMonitorVelocityVo> velocityVos = new ArrayList<MqServerMonitorVelocityVo>();
		for (ActiveMqConfiguration activeMqConfiguration : configurations) {
			MqServerMonitorVelocityVo vo = new MqServerMonitorVelocityVo();
			ActiveMqJmxConfiguration jmxConfiguration = (ActiveMqJmxConfiguration)activeMqConfiguration;

			BrokerInfo brokerInfo = this.mqService.collectBrokerInfo(jmxConfiguration,true);
			vo.setBrokerId(brokerInfo.getBrokerId());
			if (null == brokerInfo.getQueueInfos()) {
				vo.setWarning(1);
				vo.setContent("无法解析Broker Queue信息，请检查服务");
			}else {
				boolean hasMonitorQueue = false;
				for (BrokerQueueInfo queueInfo : brokerInfo.getQueueInfos()) {
					if (queueInfo.getQueueName().equals("monitorAccessQueue")) {
						hasMonitorQueue = true;
						List<Object> datas = ActiveMqUtil.receiveAndAckMessages(
								jmxConfiguration.getMqUsername(),
								jmxConfiguration.getMqPassword(),
								"tcp://" + jmxConfiguration.getHostIp() + ":" + jmxConfiguration.getMqPort() , 
								"monitorAccessQueue");
						if (datas.isEmpty()) {
							vo.setWarning(1);
							vo.setContent("未检测到心跳queue信息，请检查服务");
						}else {
							vo.setWarning(0);
							StringBuilder builder = new StringBuilder();
							for (int i = 0; i < datas.size(); i++) {
								this.appendTimeInfo(builder, datas.get(i).toString());
								if (i < datas.size() - 1) {
									builder.append("</br>");
								}
							}
							vo.setContent(builder.toString());
						}
						break;
					}
				}
				if (!hasMonitorQueue) {
					vo.setWarning(1);
					vo.setContent("缺少心跳queue，请检查服务");
				}
			}
			velocityVos.add(vo);
		}
		//填充VO
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("reportDate", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm"));
		model.put("serverStatus", velocityVos);
		PropertiesUtil pu = new PropertiesUtil("application.properties");
		this.threadPoolTaskExecutor.execute(MailSender.getInstance(mailSenderService, "ActiveMQ Server Status Tunning", model, pu.getProperty("monitor.activemq.mail.to").split(","), "velocity/api_borker_status_monitor.vm"));
		this.logger.info(" End to monitor MQ Serve status task.");
	}
	
	private void appendTimeInfo(StringBuilder builder, String info) {
		if (StringUtils.isEmpty(info)) {
			return;
		}
		if (info.startsWith("{")) {
			info = info.substring(1, info.length());
		}
		if (info.endsWith("}")) {
			info = info.substring(0, info.length() - 1);
		}
		String[] mapStr = info.split("=");
		if (mapStr.length > 2) {
			builder.append(mapStr[1]);
		}
	}
	
}
