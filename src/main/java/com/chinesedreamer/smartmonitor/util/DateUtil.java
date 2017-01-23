package com.chinesedreamer.smartmonitor.util;

import java.util.Date;

/**
 * Description:
 * Auth:Paris
 * Date:Jan 23, 2017
**/
public class DateUtil {
	public static int intervalMinutes(Date date1, Date date2) {
		long interval = Math.abs(date1.getTime() - date2.getTime());
		return (int)(interval / 1000 / 60);
	}
}
