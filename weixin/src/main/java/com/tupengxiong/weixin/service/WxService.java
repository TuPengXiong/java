package com.tupengxiong.weixin.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class WxService {

	/**
	 * 验证签名/消息验证
	 * 
	 * @param params
	 * @return
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
}
