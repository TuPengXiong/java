package com.tupengxiong.HBase.utils;

public class RowKeyUtils extends Utils {
	public String getName() {
		return "行键生成工具";
	}

	public String getDescription() {
		return "用于生成行键";
	}

	public static String randomAndTime() {
		String randomStr = RandomUtils.generateString(8);
		String time = DateUtils.format(DateUtils.currentDate(), DateUtils.parsePatterns[16]);
		return randomStr + time;
	}

	public static void main(String[] args) {
		System.out.println(randomAndTime());
	}
}