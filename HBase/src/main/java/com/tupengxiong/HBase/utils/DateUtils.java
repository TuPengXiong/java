package com.tupengxiong.HBase.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils extends Utils {
	public static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
			"yyyy.MM.dd HH:mm", "yyyy.MM", "yyyyMMdd", "yyyyMMddHHmmss", "yyyyMMddHHmm", "yyyyMM", "yyyyMMddHH"};

	public String getName() {
		return "日期工具";
	}

	public String getDescription() {
		return "处理日期的工具，可用于日期的计算，格式化，获取当前日期等";
	}

	public static DateFormat getDateFormat(String formatString) {
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern(formatString);
		return format;
	}

	public static String format(Date date, String formatString) {
		DateFormat format = getDateFormat(formatString);
		return format.format(date);
	}

	public static String format(Date date) {
		DateFormat format = getDateFormat(parsePatterns[1]);
		return format.format(date);
	}

	public static Date currentDate() {
		return new Date(System.currentTimeMillis());
	}

	public static void main(String[] args) {
		System.out.println(format(new Date(), parsePatterns[12]));
	}
}