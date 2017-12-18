package com.tupengxiong.HBase.utils;

import java.util.Date;
import java.util.UUID;

public class SystemUtils extends Utils {
	public String getName() {
		return "系统工具";
	}

	public String getDescription() {
		return "可用于获取系统参数，获取系统时间等用途";
	}

	public static String getSystemProperty(String property) {
		try {
			return System.getProperty(property);
		} catch (SecurityException ex) {
			System.err.println("Caught a SecurityException reading the system property '" + property
					+ "'; the SystemUtils property value will default to null.");
		}
		return null;
	}

	public static String uuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static Date currentDate() {
		return new Date(System.currentTimeMillis());
	}

	public static void main(String[] args) {
		System.out.println(uuid());
	}
}