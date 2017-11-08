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
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.tupengxiong.weixin.redis.RedisPool;

@Service
public class WxService implements InitializingBean {
	/**
	 * WxService APPID
	 */
	public final static String APPID = "wx23d70d36f886f949";
	/**
	 * APPSECRET
	 */
	public final static String APPSECRET = "d4624c36b6795d1d99dcf0547af5443d";
	/**
	 * 获取access_token URL
	 */
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";

	/**
	 * 发送客服消息URL
	 */
	public static final String SEND_KEFU_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send";

	/**
	 * 发送模板消息URL
	 */
	public static final String SEND_TEMPLATE_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send";

	private static final Logger logger = Logger.getLogger(WxService.class);

	@Resource
	private RestTemplate restTemplate;

	@Resource
	private RedisPool redisPool;

	/**
	 * getSignature:验证签名/消息验证. <br/>
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
	 * 
	 * @author tupengxiong
	 * @param APPID
	 * @param APPSECRET
	 * @return
	 * @since JDK 1.7
	 */
	public String getAccessToken(boolean refresh) {
		String token = redisPool.get(APPID);
		if (token != null && !refresh) {
			logger.info("access_token" + token);
			return token;
		}
		URI uri = null;
		try {
			uri = UriComponentsBuilder.fromHttpUrl(ACCESS_TOKEN_URL).queryParam("grant_type", "client_credential")
					.queryParam("APPID", APPID).queryParam("secret", APPSECRET).build().encode("UTF-8").toUri();
		} catch (UnsupportedEncodingException e) {
			logger.error(new StringBuilder("WxService  getAccessToken UnsupportedEncodingException"));
			return null;
		}
		ResponseEntity<String> respEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);
		if (respEntity != null && respEntity.getBody() != null) {
			try {
				logger.info(respEntity.getBody());
				JSONObject json = new JSONObject(respEntity.getBody());
				if (json.getString("access_token") == null && !refresh) {
					getAccessToken(true);
				} else {
					String access_token = json.getString("access_token");
					Integer expires_in = json.getInt("expires_in");
					redisPool.set(APPID, access_token);
					redisPool.expire(APPID, expires_in);
				}
			} catch (JSONException e) {
				logger.error(new StringBuilder("WxService  getAccessToken").append(respEntity));
				return null;
			}
		}
		return null;
	}

	public Map<String, Object> sendKefuMsg(JSONObject json) {
		Map<String, Object> map = new HashMap<String, Object>();
		URI uri = null;
		try {
			uri = UriComponentsBuilder.fromHttpUrl(SEND_KEFU_MSG_URL).queryParam("access_token", getAccessToken(false))
					.build().encode("UTF-8").toUri();
		} catch (UnsupportedEncodingException e) {
			logger.error("sendKefuMsg UnsupportedEncodingException");
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> reqEntity = new HttpEntity<String>(json.toString(), headers);
		ResponseEntity<String> respEntity = restTemplate.exchange(uri, HttpMethod.POST, reqEntity, String.class);
		logger.info(respEntity.getBody());
		if (!StringUtils.isBlank(respEntity.getBody())) {
			JSONObject object = new JSONObject(respEntity.getBody().toString());
			map.put("errmsg", object.getString("errmsg"));
		}
		return map;
	}

	public String sendTemplateMsg(JSONObject json) {
		URI uri = null;
		try {
			uri = UriComponentsBuilder.fromHttpUrl(SEND_TEMPLATE_MSG_URL)
					.queryParam("access_token", getAccessToken(false)).build().encode("UTF-8").toUri();
		} catch (UnsupportedEncodingException e) {
			logger.error("sendTemplateMsg UnsupportedEncodingException");
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> reqEntity = new HttpEntity<String>(json.toString(), headers);
		if (null == restTemplate) {
			restTemplate = new RestTemplate();
		}
		ResponseEntity<String> respEntity = restTemplate.exchange(uri, HttpMethod.POST, reqEntity, String.class);
		logger.info(respEntity.getBody());
		return respEntity.getBody();
	}

	/**
	 * 发送模板消息
	 * 
	 * @return { "errcode":0, "errmsg":"ok" }
	 */
	public boolean sendTemplate(String toUser, String templateId, String url, String... value) {

		Map<String, Object> map = new HashMap<String, Object>();

		Map<String, Object> json = new HashMap<String, Object>();

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("value", value[0]);
		data.put("color", "#173177");
		map.put("first", data);

		for (int i = 1; i < value.length - 1; i++) {
			data = new HashMap<String, Object>();
			data.put("value", value[i]);
			data.put("color", "#173177");
			map.put("keyword" + i, data);
		}
		data = new HashMap<String, Object>();
		data.put("value", value[value.length - 1]);
		data.put("color", "#173177");
		map.put("remark", data);

		json.put("touser", toUser);
		json.put("template_id", templateId);
		json.put("url", url);
		json.put("data", map);

		JSONObject object = new JSONObject(json);

		String resp = sendTemplateMsg(object);
		JSONObject respJson = new JSONObject(resp);
		if (null != respJson && respJson.get("errmsg").equals("ok") && respJson.get("msgid") != null) {
			return true;
		}
		return false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info(new StringBuilder().append("AccessTokenTask InitializingBean starting-------"));
		this.getAccessToken(true);
	}
}
