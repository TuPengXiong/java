package com.tupengxiong.weixin.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 图灵机器人
 */
public class TuLingTools {

	private static final String api_ket = "4aa238fb3ff04fc4bb964060c8485e74";
	private static final String api_secret = "3ed93627ba6ee059";
	private static final String url = "http://www.tuling123.com/openapi/api";
	

	/**
	 * 获取图灵的返回信息
	 * @param userId
	 * @param info
	 * @param defultString
	 * @return
	 */
	public static String getResponseFromTuLing(String userId, String info, String defultString) {
		URI uri = null;
		try {
			uri = UriComponentsBuilder.fromHttpUrl(url).build().encode("UTF-8").toUri();
		} catch (UnsupportedEncodingException e) {
			return defultString;
		}
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("key", api_ket);
		jsonObject.put("info", info);
		jsonObject.put("userId", userId);
		HttpEntity<String> reqEntity = new HttpEntity<String>(jsonObject.toString(), headers);
		ResponseEntity<String> respEntity = restTemplate.exchange(uri, HttpMethod.POST, reqEntity, String.class);
		if (null != respEntity) {
			if (!StringUtils.isBlank(respEntity.getBody().toString())) {
				JSONObject object = new JSONObject(respEntity.getBody().toString());
				if (object.getInt("code") == 100000) {
					return (String) respEntity.getBody();
				}
			}
		}
		
		return defultString;
	}

	
	public static void main(String[] args) {
		System.out.println(getResponseFromTuLing("3ed93627ba6ee059", "12", "error"));
	}
}
