package com.tupengxiong.rocketmq;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		Map<String, String> params = new HashMap<>();

		params.put("merchant_no", "10001");
		
		
		params.put("serial_no", "20170927000000001");
		params.put("plan_id", "test1");
		params.put("limit_period", "7");
		params.put("start_time", "1506441600000");
		params.put("plan_amount", "100000");
		params.put("valid_time", "1506441600000");
		
		params.put("key", "11111111111111111");
		params.put("sign", DigestUtils.md5Hex(createSign(params, true)));
		System.out.println(params);
		
		
		String sign = params.get("sign");
		params.remove("sign");
		//
		String verifySign = DigestUtils.md5Hex(createSign(params, true));
		System.out.println(sign.equals(verifySign));
	}

	/**
	 * 构造签名
	 * 
	 * @param params
	 * @param encode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String createSign(Map<String, String> params, boolean encode) {
		Set<String> keysSet = params.keySet();
		Object[] keys = keysSet.toArray();
		Arrays.sort(keys);
		StringBuffer temp = new StringBuffer();
		boolean first = true;
		for (Object key : keys) {
			if (first) {
				first = false;
			} else {
				temp.append("|");
			}
			Object value = params.get(key);
			String valueString = "";
			if (null != value) {
				valueString = value.toString();
			}
			if (encode) {
				try {
					temp.append(URLEncoder.encode(valueString, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {
				temp.append(valueString);
			}
		}
		return temp.toString();
	}
}
