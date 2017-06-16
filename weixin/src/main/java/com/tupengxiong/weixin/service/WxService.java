package com.tupengxiong.weixin.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.cloudy.fastjson.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.tupengxiong.weixin.redis.Redis;

@Service
public class WxService {

	/**
	 * 获取access_token
	 */
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

	/**
	 * 发送客服消息
	 */
	public static final String SEND_KEFU_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send";

	private static final Logger logger = Logger.getLogger(WxService.class);

	@Resource
	RestTemplate restTemplate;

	@Resource
	Redis redisSingleton;

	/**
	 * getSignature:验证签名/消息验证. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 * 
	 * @author tupengxiong
	 * @param params
	 * @return
	 * @throws Exception
	 * @since JDK 1.7
	 */
	public boolean getSignature(Map<String, String> params) throws Exception {

		String signature = params.get("signature");
		String msg_encrypt = params.get("msg_encrypt");
		String msg_signature = params.get("msg_signature");
		String token = params.get("token");
		String timestamp = params.get("timestamp");
		String nonce = params.get("nonce");
		// 1）将token、timestamp、nonce三个参数进行字典序排序
		List<String> list = new ArrayList<String>();
		/** 消息加密 **/
		if (null != msg_encrypt && null != msg_signature) {
			list.add(token);
			list.add(timestamp);
			list.add(nonce);
			list.add(msg_encrypt);
		} else {
			// 签名验证
			list.add(nonce);
			list.add(timestamp);
			list.add(token);
		}
		Collections.sort(list);
		// 2）将三个参数字符串拼接成一个字符串进行sha1加密
		String newSignature;
		if (list.size() == 3) {
			newSignature = DigestUtils.shaHex(list.get(0) + list.get(1) + list.get(2));
			// 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
			return newSignature.equals(signature);
		} else {
			newSignature = DigestUtils.shaHex(list.get(0) + list.get(1) + list.get(2) + list.get(3));
			// 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
			return newSignature.equals(signature);
		}
	}

	/**
	 * getAccessToken:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 * 
	 * @author tupengxiong
	 * @param appId
	 * @param appSecret
	 * @return
	 * @since JDK 1.7
	 */
	public String getAccessToken(String appId, String appSecret, boolean refresh) {
		if (redisSingleton.getJedisInPool().get(appId) != null && !refresh) {
			return redisSingleton.getJedisInPool().get(appId);
		}
		URI uri = null;
		try {
			uri = UriComponentsBuilder.fromHttpUrl(ACCESS_TOKEN_URL).queryParam("grant_type", "client_credential")
					.queryParam("appId", appId).queryParam("secret", appSecret).build().encode("UTF-8").toUri();
		} catch (UnsupportedEncodingException e) {
			logger.error(new StringBuilder("WxService  getAccessToken UnsupportedEncodingException"));
			return null;
		}
		ResponseEntity<String> respEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);
		if (respEntity != null && respEntity.getBody() != null) {
			try {
				logger.info(respEntity.getBody());
				JSONObject json = new JSONObject(respEntity.getBody());
				String access_token = json.getString("access_token");
				Integer expires_in = json.getInt("expires_in") - 200;
				redisSingleton.getJedisInPool().append(appId, access_token);
				redisSingleton.getJedisInPool().expire(appId, expires_in);
			} catch (JSONException e) {
				logger.error(new StringBuilder("WxService  getAccessToken").append(respEntity));
				return null;
			}
		}
		return null;
	}

	public Map<String, Object> sendKefuMsg(String appId, JSONObject json) {
		Map<String, Object> map = new HashMap<String, Object>();
		URI uri = null;
		try {
			uri = UriComponentsBuilder.fromHttpUrl(SEND_KEFU_MSG_URL)
					.queryParam("access_token", redisSingleton.getJedisInPool().get(appId)).build().encode("UTF-8")
					.toUri();
		} catch (UnsupportedEncodingException e) {
			logger.error("TuLingTools  getResponseFromTuLing UnsupportedEncodingException");
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> reqEntity = new HttpEntity<String>(json.toString(), headers);
		ResponseEntity<String> respEntity = restTemplate.exchange(uri, HttpMethod.POST, reqEntity, String.class);
		if (null != respEntity) {
			if (!StringUtils.isBlank(respEntity.getBody().toString())) {
				JSONObject object = new JSONObject(respEntity.getBody().toString());
				logger.info(respEntity.getBody());
				map.put("errmsg", object.getString("errmsg"));
			}
		}
		return map;
	}
}
