package com.chinesedreamer.smartmonitor.tools.mail;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinesedreamer.smartmonitor.tools.mail.service.MailSenderService;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 23, 2017
**/
public class MailSender implements Runnable{
	private Logger logger = LoggerFactory.getLogger(MailSender.class);

	private MailSenderService mailSenderService;
	private String[] to;
	private String[] cc;
	private String[] bcc;
	private String subject;
	private Map<String, Object> model;
	private String templatePath;
	
	public MailSender(MailSenderService mailSenderService,String[] to,String subject,Map<String, Object> model,String templatePath){
		this.mailSenderService = mailSenderService;
		this.to = to;
		this.subject = subject;
		this.model = model;
		this.templatePath = templatePath;
	}
	
	public MailSender(MailSenderService mailSenderService,String[] to,String[] cc,String subject,Map<String, Object> model,String templatePath){
		this.mailSenderService = mailSenderService;
		this.to = to;
		this.cc = cc;
		this.subject = subject;
		this.model = model;
		this.templatePath = templatePath;
	}
	
	public MailSender(MailSenderService mailSenderService,String[] to,String[] cc,String[] bcc,String subject,Map<String, Object> model,String templatePath){
		this.mailSenderService = mailSenderService;
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.subject = subject;
		this.model = model;
		this.templatePath = templatePath;
	}

	@Override
	public void run() {
		try {
			this.mailSenderService.sendMail(to, cc, bcc, subject, model, templatePath);
		} catch (Exception e) {
			this.logger.error("{}",e);
		}
	}

	public static MailSender getInstance(MailSenderService mailSenderService, String subject,Map<String, Object> model,String[] to,String templatePath){
		return new MailSender(mailSenderService, to, subject, model, templatePath);
	}
	
	public static MailSender getInstance(MailSenderService mailSenderService, String subject,Map<String, Object> model,String[] to,String[] cc,String templatePath){
		return new MailSender(mailSenderService, to,cc, subject, model, templatePath);
	}
	
	public static MailSender getInstance(MailSenderService mailSenderService, String subject,Map<String, Object> model,String[] to,String[] cc,String[] bcc,String templatePath){
		return new MailSender(mailSenderService, to,cc,bcc, subject, model, templatePath);
	}
}
