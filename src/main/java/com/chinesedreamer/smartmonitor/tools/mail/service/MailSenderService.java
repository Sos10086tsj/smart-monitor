package com.chinesedreamer.smartmonitor.tools.mail.service;

import java.util.Map;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 23, 2017
**/
public interface MailSenderService {
	public void sendMail(String[] to, String[] cc, String[] bcc, String subject, Map<String,Object> model, String templatePath);
}
