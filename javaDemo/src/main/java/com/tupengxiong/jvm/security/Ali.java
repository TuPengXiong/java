/** 
 * Project Name:javaDemo 
 * File Name:Ali.java 
 * Package Name:com.tupengxiong.jvm.security 
 * Date:2018年8月8日上午1:17:00 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.tupengxiong.jvm.security;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/** 
 * ClassName:Ali <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年8月8日 上午1:17:00 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class Ali {

	public static String url = "https://openapi.alipaydev.com/gateway.do";
	public static String appId = "2016073100134299";
	
	public static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs0MZ1NrPejKB3Uq1LMq0/GBPw/Mfvb9tcAlXEAt8t89gksig8iBi/D+jXHkDFM0XqXnK0sRrIxK1qCnMNpxefqRNyC7EGC10u/pJHs+M1s6CKoONlUkwezeQikdyPkwGn19NeOXUeTBxiOQjiy7+O7FBFcW1jxuSktRGyt+eIrx9pDEPYI8Na55rbpmjbQlffZ6D4BDk6wPDccz5S8niuBKFBo29IKsLT3q4ONGCsqNPZDUqRNlrKyo0GRS4685YLM8Agz5Hipu6TlLPSwtwcPL+IZl4bTP9iThW3CmvqrpXvKj1xUiMPkGu7HRnuKGj+XjFRPe9BQ3EBem5cvGTUQIDAQAB";
	public static String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCzQxnU2s96MoHdSrUsyrT8YE/D8x+9v21wCVcQC3y3z2CSyKDyIGL8P6NceQMUzRepecrSxGsjErWoKcw2nF5+pE3ILsQYLXS7+kkez4zWzoIqg42VSTB7N5CKR3I+TAafX0145dR5MHGI5COLLv47sUEVxbWPG5KS1EbK354ivH2kMQ9gjw1rnmtumaNtCV99noPgEOTrA8NxzPlLyeK4EoUGjb0gqwtPerg40YKyo09kNSpE2WsrKjQZFLjrzlgszwCDPkeKm7pOUs9LC3Bw8v4hmXhtM/2JOFbcKa+qule8qPXFSIw+Qa7sdGe4oaP5eMVE970FDcQF6bly8ZNRAgMBAAECggEAAgNe5uUOL3EhxDpyjm44Wh06yBiS4q6jq/5u299FJ0tM6lkWdaGneu28B+3T+wfSnDSh8nwCOAKdx6WwhWx8Iy/1L6pWyW65QOMurwnlqwPmslOH44VO5hNZrDPhNJHmASQw2oq6OCIzJDZrr8R3VnRHJtdxkoncu4lmwiCAxiMS8hgGfq9wJZsWx21NbLCktAMilikWlb79FF2DAwwKgzjLEpdYkg/5SkIwlRhUkVe+RqDAxd5HmRHHofN/fdEeCS1KVqqInELtM0Hy9g2Eu4021kvJBjK8SeT2O0jwhp7ACwWTSjPGJ4L5ukJlUac1NXISekBb27sCFsOw7lelgQKBgQDo06IxjX9bbk+AJ/7htuaDLzC7IFdrvGa/i8f7D/7kXkCH9zfdb+Ine2zTWb4plVAoh9xqERZQbRwtpKjJE0T0otW/Htpv7Jh63/IRE4633ZePYJor9R1vOTO2kbAEPRicwj+DTsCYXDY4AXUKenSPP5kQ5U7Zi1FLKUB7vduTCwKBgQDFGqe0iKZcwb97GKnDzRGuKk+psIiEt84ejtt4eVb5CsHi1teOgUqBqRrRpFLFDemLmebfSlo/JIg3PnaQx6EXnEuCzG85bXpkPJY7bvj4NBJBAt4k0ptBkl2gNICxJYkm4jJh2D97FoV34Tcpv2JYrDX+ABCAqW9RSfR6N6rskwKBgB1jGQXIJlsUAVTbt4Al5dKJEk2MN3yRuyZSLluyGSoZ+2st+Q8qIBF7srC6kxYMkqGLBHce0QI1w2i/b85xcDKwmuoUqt2Vr2lS+urM3Sa4AXlHaC5EMgLn5W8V1HG0hHbEzd91ATo56V4IUQ2Rh0TNcjR/vQQYYZprCoiT3jMhAoGAEAMWVKg1O5vRvmJGiE2Efi2ZwyNAM+fqqrjYQ3U4B4tELPVfFYiTUO037If44WE788dQ5hrYMgD5v+MnJqPRBmYADGQnNPcb1kDFw5ZES4WPZhChk0Q4sJ7/VCBvw/RUq//8L86teYZe2VpGbPHLP4Dd8gB3Vrxs+qGTZspW7FkCgYEA0rxeXfZ35KunCdxN/ClEta13AUEZ3e2ESUqELrfQN9NrP6VhXVKOluOVrqOV/RWSFyNeBrOcYEfbBIz+WJBKrM4M6H7LB2QPLYz6htyCOmE01Cfd4rjZfeG/39UwdCV0lNYBy+J+Ih2/sqJUGWBPBZqjhnEo1En4STuBeUyk+wI=";

	public static void main(String[] args) {
		
		Map<String,Object> dataMap = new TreeMap<String,Object>();
		dataMap.put("subject", "Iphone6");
		dataMap.put("out_trade_no", ""+System.currentTimeMillis());
		dataMap.put("total_amount", "9.00");
		dataMap.put("qr_code_timeout_express", "90m");
		Map<String,Object> map = new TreeMap<String,Object>();
		map.put("app_id", appId);
		map.put("method", "alipay.trade.precreate");
		//map.put("format", "JSON");
		map.put("charset", "utf-8");
		map.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		map.put("version", "1.0");
		//map.put("notify_url", "https://www.lovesher.com/common/aliPay/notify");
		map.put("biz_content", JSON.toJSONString(dataMap));
		map.put("sign_type", "RSA2");
		String content = RSA.getSignContent(map);
		
		String sign = RSA.sign(content, privateKey);
		map.put("sign", sign);
		
		
		System.out.println(content);
		System.out.println(sign);
		String resp = HttpUtils.post(url, map);
		
		map.remove("sign");
		System.out.println(RSA.verify(content, sign, publicKey));
		
		System.out.println(resp);
		System.out.println(RSA.verify(content, sign, publicKey));
		
		System.out.println(JSON.parseObject(resp).getJSONObject("alipay_trade_precreate_response").getString("qr_code"));
		
		JSONObject respJson = JSON.parseObject(resp);
		content = respJson.getString("alipay_trade_precreate_response").replaceAll("/", "\\/");
		System.out.println("content--------" +content.replaceAll("/", "\\/"));
		
		sign = respJson.getString("sign");
		System.out.println("sign--------" +sign);
		System.out.println(RSA.verify(content, sign, publicKey));
	}
}
  