/** 
 * Project Name:ConsumerWxText 
 * File Name:ConsumerWxText.java 
 * Package Name:com.tupengxiong.weixin.task.consumer
 * Date:2017年11月3日下午2:24:20 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.weixin.task.consumer;

import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.tupengxiong.weixin.bean.WxText;
import com.tupengxiong.weixin.bean.enums.WxTextSendStatusEnum;
import com.tupengxiong.weixin.bean.mapper.WxTextMapper;
import com.tupengxiong.weixin.context.SpringExt;
import com.tupengxiong.weixin.service.WxService;
import com.tupengxiong.weixin.task.Consumer;
import com.tupengxiong.weixin.task.Producer;

/**
 * ClassName:Consumer <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年11月3日 下午2:24:20 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class ConsumerWxText extends Consumer<WxText> {
	private static final Logger logger = Logger.getLogger(ConsumerWxText.class);
	private static final String openId = "otuUIwwrKgYPWIngIkHkiDxfdmSQ";

	public ConsumerWxText(WxText obj, Producer<WxText> producer) {
		super(obj, producer);
	}

	@Override
	public void cosumer() {
		WxTextMapper wxTextMapper = (WxTextMapper) SpringExt.getBean("wxTextMapper");
		WxService wxService = (WxService) SpringExt.getBean("wxService");
		JSONObject json = new JSONObject();
		json.put("touser", openId);
		json.put("msgtype", "text");
		JSONObject jsonContent = new JSONObject();
		jsonContent.put("content", this.getObj().getContent() + "\r\n[" + this.getObj().getFromUserName() + "]");
		json.put("text", jsonContent);
		logger.debug(json.toString());
		Map<String, Object> map = wxService.sendKefuMsg(json);
		WxText wxText2 = new WxText();
		wxText2.setId(this.getObj().getId());
		if (null !=map.get("errmsg") && map.get("errmsg").equals("ok")) {
			wxText2.setSendStatus(WxTextSendStatusEnum.SUCCESS.getStatus());
			wxTextMapper.update(wxText2);
		}else{
			wxText2.setSendStatus(WxTextSendStatusEnum.FAIL.getStatus());
			wxTextMapper.update(wxText2);
		}
	}
}
