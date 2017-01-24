package com.chinesedreamer.smartmonitor.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 20, 2017
**/
public class ActiveMqUtil {
	
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
}
