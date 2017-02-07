package com.chinesedreamer.smartmonitor.util;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConversionException;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 20, 2017
**/
public class ActiveMqUtil {
	private static Logger logger = LoggerFactory.getLogger(ActiveMqUtil.class);
	
	public static String getUptimeStr(long uptime) {
		NumberFormat fmtI = new DecimalFormat("###,###", new DecimalFormatSymbols(Locale.ENGLISH));
        NumberFormat fmtD = new DecimalFormat("###,##0.000", new DecimalFormatSymbols(Locale.ENGLISH));

        if (uptime < 60) {
            return fmtD.format(uptime) + " seconds";
        }
        uptime /= 60;
        if (uptime < 60) {
            long minutes = (long) uptime;
            String s = fmtI.format(minutes) + (minutes > 1 ? " minutes" : " minute");
            return s;
        }
        uptime /= 60;
        if (uptime < 24) {
            long hours = (long) uptime;
            long minutes = (long) ((uptime - hours) * 60);
            String s = fmtI.format(hours) + (hours > 1 ? " hours" : " hour");
            if (minutes != 0) {
                s += " " + fmtI.format(minutes) + (minutes > 1 ? " minutes" : " minute");
            }
            return s;
        }
        uptime /= 24;
        long days = (long) uptime;
        long hours = (long) ((uptime - days) * 24);
        String s = fmtI.format(days) + (days > 1 ? " days" : " day");
        if (hours != 0) {
            s += " " + fmtI.format(hours) + (hours > 1 ? " hours" : " hour");
        }
        return s;
	}
	
	public static long getUptime(String uptime){
		long time = 0l;
		if (StringUtils.isEmpty(uptime) || uptime.equals("not started")) {
			return time;
		}
		//days
		int daysIndex = uptime.indexOf("days");
		if (daysIndex != -1) {
			int days = Integer.parseInt(uptime.substring(0, daysIndex -1).trim());
			time += days * 24 * 60 * 60;
			uptime = uptime.substring(daysIndex + "days".length());
		}
		//day
		int dayIndex = uptime.indexOf("days");
		if (dayIndex != -1) {
			int day = Integer.parseInt(uptime.substring(0, dayIndex -1).trim());
			time += day * 24 * 60 * 60;
			uptime = uptime.substring(dayIndex + "day".length());
		}
		//hours
		int hoursIndex = uptime.indexOf("hours");
		if (hoursIndex != -1) {
			int hours = Integer.parseInt(uptime.substring(0, hoursIndex -1).trim());
			time += hours * 60 * 60;
			uptime = uptime.substring(hoursIndex + "hours".length());
		}
		//hour
		int hourIndex = uptime.indexOf("hour");
		if (hourIndex != -1) {
			int hour = Integer.parseInt(uptime.substring(0, hourIndex -1).trim());
			time += hour * 60 * 60;
			uptime = uptime.substring(hourIndex + "hour".length());
		}
		//minutes
		int minutesIndex = uptime.indexOf("minutes");
		if (minutesIndex != -1) {
			int minutes = Integer.parseInt(uptime.substring(0, minutesIndex -1).trim());
			time += minutes * 60;
			uptime = uptime.substring(minutesIndex + "minutes".length());
		}
		//minute
		int minuteIndex = uptime.indexOf("minute");
		if (minuteIndex != -1) {
			int minute = Integer.parseInt(uptime.substring(0, minuteIndex -1).trim());
			time += minute * 60;
			uptime = uptime.substring(minuteIndex + "minute".length());
		}
		//seconds
		int secondsIndex = uptime.indexOf("seconds");
		if (secondsIndex != -1) {
			int seconds = Integer.parseInt(uptime.substring(0, secondsIndex -1).trim());
			time += seconds;
		}
		
		return time;
	}
	
	public static Object extractMessage(Message message) throws JMSException, MessageConversionException {
		if (message instanceof TextMessage) {
			return extractStringFromMessage((TextMessage) message);
		} else if (message instanceof BytesMessage) {
			return extractByteArrayFromMessage((BytesMessage) message);
		} else if (message instanceof MapMessage) {
			return extractMapFromMessage((MapMessage) message);
		} else if (message instanceof ObjectMessage) {
			return extractSerializableFromMessage((ObjectMessage) message);
		} else {
			return message;
		}
	}
	
	public static String extractStringFromMessage(TextMessage message) throws JMSException {  
        return message.getText();  
    }
	
	public static byte[] extractByteArrayFromMessage(BytesMessage message) throws JMSException {  
        byte[] bytes = new byte[(int) message.getBodyLength()];  
        message.readBytes(bytes);  
        return bytes;  
    }  
	
	public static Map<String,Object> extractMapFromMessage(MapMessage message) throws JMSException {  
        Map<String, Object> map = new HashMap<String, Object>();  
        @SuppressWarnings("rawtypes")
		Enumeration en = message.getMapNames();  
        while (en.hasMoreElements()) {  
            String key = (String) en.nextElement();  
            map.put(key, message.getObject(key));  
        }  
        return map;  
    }  
   
	public static Serializable extractSerializableFromMessage(ObjectMessage message) throws JMSException {  
        return message.getObject();  
    }
	
	public static List<Object> receiveAndAckMessages(String brokerUrl, String queueName) {
		return receiveAndAckMessages("admin", "admin", brokerUrl, queueName);
	}
	
	/**
	 * 接收并ack 消息
	 * @param username
	 * @param password
	 * @param brokerUrl
	 * @param queueName
	 * @return
	 */
	public static List<Object> receiveAndAckMessages(String username, String password, String brokerUrl, String queueName) {
		List<Object> datas = new ArrayList<Object>();
		try {
			ConnectionFactory factory = new ActiveMQConnectionFactory(username, password, brokerUrl);
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(queueName);
			MessageConsumer consumer = session.createConsumer(destination);
			connection.start();
			boolean hasMessage = true;
			int messageNum = 0;
			while (hasMessage && messageNum <= 3) {
				Message message = consumer.receive();
				if (null == message) {
					hasMessage = false;
					break;
				}
				Object data = extractMessage(message);
				datas.add(data);
				messageNum ++;
			}
			consumer.close();
			session.close();
			connection.stop();
			connection.close();
		} catch (Exception e) {
			logger.error("{}",e);
		}
		return datas;
	}
}
