package com.chinesedreamer.smartmonitor.tools.mail.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.chinesedreamer.smartmonitor.tools.mail.model.MailConfig;
import com.chinesedreamer.smartmonitor.tools.mail.service.MailSenderService;
import com.chinesedreamer.smartmonitor.util.MailUtil;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 23, 2017
**/
@Service
public class MailSenderServiceImpl implements MailSenderService{
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	private Logger logger = LoggerFactory.getLogger(MailSenderServiceImpl.class);

	@Override
	public void sendMail(String[] to, String[] cc, String[] bcc, String subject, Map<String, Object> model,
			String templatePath) {
		logger.info("begin sendTemplateEmail...");
		
		MailConfig mailConfig = MailUtil.getConfig();
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(mailConfig.getHost());
		sender.setUsername(mailConfig.getUsername());
		sender.setPassword(mailConfig.getPassword());
		
		MimeMessage mailMessage = sender.createMimeMessage();		
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
			messageHelper.setTo(to);
			messageHelper.setFrom(mailConfig.getFrom());
			if (null != cc) {
				messageHelper.setCc(cc);
			}
			if (null != bcc) {
				messageHelper.setBcc(bcc);
			}
			messageHelper.setSubject(mailConfig.getEnv() + subject);
			String text = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, templatePath, "UTF-8", model);
			messageHelper.setText(text, true);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.timeout", "25000");
		
		sender.setJavaMailProperties(prop);
		
		sender.send(mailMessage);
		
		logger.info("sendTemplateEmail success! Sent date: {}, subject:{}", new Date(), subject);
	}

}
