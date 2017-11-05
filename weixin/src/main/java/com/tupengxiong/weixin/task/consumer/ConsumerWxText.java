/** 
 * Project Name:ConsumerWxText 
 * File Name:ConsumerWxText.java 
 * Package Name:com.tupengxiong.weixin.task.consumer
 * Date:2017年11月3日下午2:24:20 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.weixin.task.consumer;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

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
	private static final String OPENID = "otuUIwwrKgYPWIngIkHkiDxfdmSQ";
	private static final String TEMPLATEID = "GOfCnFlvl5BqDhjB7s9Q1wP63Rkw5YGaHyrP9FPextQ";
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public ConsumerWxText(WxText obj, Producer<WxText> producer) {
		super(obj, producer);
	}

	@Override
	public void cosumer() {
		WxTextMapper wxTextMapper = (WxTextMapper) SpringExt.getBean(WxTextMapper.class);
		WxService wxService = (WxService) SpringExt.getBean("wxService");
		WxText wxText2 = new WxText();
		wxText2.setId(this.getObj().getId());
		if(wxService.sendTemplate(OPENID, TEMPLATEID, "https://www.lovesher.com/common/",
				new String[] {this.getObj().getFromUserName(), this.getObj().getContent(),
						format.format(this.getObj().getCreateTime()),this.getObj().getName() })){
			logger.info("SUCCEES==>"+this.getObj().getId());
			wxText2.setSendStatus(WxTextSendStatusEnum.SUCCESS.getStatus());
			wxTextMapper.update(wxText2);
		}else {
			logger.info("FAILED==>"+this.getObj().getId());
			wxText2.setSendStatus(WxTextSendStatusEnum.FAIL.getStatus());
			wxTextMapper.update(wxText2);
		}
		/*JSONObject json = new JSONObject();
		json.put("touser", OPENID);
		json.put("msgtype", "text");
		JSONObject jsonContent = new JSONObject();
		jsonContent.put("content", this.getObj().getContent() + "\r\n[" + this.getObj().getFromUserName() + "]");
		json.put("text", jsonContent);
		logger.debug(json.toString());
		Map<String, Object> map = wxService.sendKefuMsg(json);
		
		if (null != map.get("errmsg") && map.get("errmsg").equals("ok")) {
			wxText2.setSendStatus(WxTextSendStatusEnum.SUCCESS.getStatus());
			wxTextMapper.update(wxText2);
		} else {
			wxText2.setSendStatus(WxTextSendStatusEnum.FAIL.getStatus());
			wxTextMapper.update(wxText2);
		}*/
	}
}
