package com.world.chaip.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public static Date parse(String dateString, String formatString) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat(formatString);
		return df.parse(dateString);
	}
	
	public static Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(5, now.get(5) - day);
		return now.getTime();
	}

	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(5, now.get(5) + day);
		return now.getTime();
	}
}
