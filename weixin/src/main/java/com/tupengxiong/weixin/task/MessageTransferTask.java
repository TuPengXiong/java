/** 
 * Project Name:weixin 
 * File Name:MessageTransferTask.java 
 * Package Name:com.tupengxiong.weixin.task 
 * Date:2017年6月14日下午12:01:30 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.weixin.task;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tupengxiong.weixin.bean.WxText;
import com.tupengxiong.weixin.bean.mapper.WxTextMapper;
import com.tupengxiong.weixin.service.WxService;

/**
 * ClassName:MessageTransferTask <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月14日 下午12:01:30 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
@Lazy(false)
@Component
public class MessageTransferTask implements InitializingBean {

	public final static String appId = "wx23d70d36f886f949";

	@Resource
	WxTextMapper wxTextMapper;

	@Resource
	WxService wxService;

	private static final String openId = "otuUIwwrKgYPWIngIkHkiDxfdmSQ";

	private static final Logger logger = Logger.getLogger(MessageTransferTask.class);

	/**
	 * 发送客服消息到指定用户的定时任务 2分钟 小时执行一次
	 * 
	 * @author tupengxiong
	 * @since JDK 1.7
	 */
	@Scheduled(fixedDelay = 120000)
	public void wxText() {
		Integer start = 0;
		Integer nums = 1;
		List<WxText> list = wxTextMapper.selectBySendStatus(0, start, nums);
		logger.info(new StringBuilder().append("list  size-------").append(list.size()));
		while (list.size() == nums) {
			for (WxText wxText : list) {
				JSONObject json = new JSONObject();
				json.put("touser", openId);
				json.put("msgtype", "text");
				JSONObject jsonContent = new JSONObject();
				jsonContent.put("content", wxText.getContent() + "\r\n[" + wxText.getFromUserName() + "]");
				json.put("text", jsonContent);
				logger.info(json.toString());
				Map map = wxService.sendKefuMsg(appId, json);
				if (map.get("errmsg").equals("ok")) {
					WxText wxText2 = new WxText();
					wxText2.setId(wxText.getId());
					wxText2.setSendStatus(1);
					wxTextMapper.update(wxText2);
				}
			}
			list = wxTextMapper.selectBySendStatus(0, start * nums, nums);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info(new StringBuilder().append("MessageTransferTask InitializingBean starting-------"));
	}

}
