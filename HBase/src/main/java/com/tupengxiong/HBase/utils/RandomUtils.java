package com.tupengxiong.HBase.utils;

import java.util.Random;

public class RandomUtils extends Utils {
	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String NUMBERCHAR = "0123456789";

	public String getName() {
		return "随机数生成工具";
	}

	public String getDescription() {
		return "用于生成随机数";
	}

	public static int generateInt(int max) {
		Random rand = new Random();
		return rand.nextInt(max);
	}

	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; ++i) {
			sb.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
					.charAt(random.nextInt("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
		}
		return sb.toString();
	}

	public static String generateMixString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; ++i) {
			sb.append("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
					.charAt(random.nextInt("abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".length())));
		}
		return sb.toString();
	}

	public static String generateLowerString(int length) {
		return generateMixString(length).toLowerCase();
	}

	public static String generateUpperString(int length) {
		return generateMixString(length).toUpperCase();
	}

	public static String generateZeroString(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; ++i) {
			sb.append('0');
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(generateString(8));
	}
}