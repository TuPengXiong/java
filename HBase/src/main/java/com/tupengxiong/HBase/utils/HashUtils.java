package com.tupengxiong.HBase.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils extends Utils {
	public String getName() {
		return "hash编码工具";
	}

	public String getDescription() {
		return "可用于生成hash码，加密等用途";
	}

	private static byte[] toHex(String source, String encodingType) {
		try {
			byte[] input = source.getBytes("UTF8");

			MessageDigest messageDigest = MessageDigest.getInstance(encodingType);

			messageDigest.update(input);

			return messageDigest.digest();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] toMD5Hex(String source) {
		return toHex(source, "MD5");
	}

	public static final String MD5(String source) {
		char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		try {
			byte[] md = toMD5Hex(source);

			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; ++i) {
				byte byte0 = md[i];
				str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
				str[(k++)] = hexDigits[(byte0 & 0xF)];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final String getHash(String source, String encodingType) {
		char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		try {
			byte[] md = toHex(source, encodingType);

			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; ++i) {
				byte byte0 = md[i];
				str[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];
				str[(k++)] = hexDigits[(byte0 & 0xF)];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(MD5("nihao"));
		System.out.println(getHash("nihao", "SHA"));
	}
}