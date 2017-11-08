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
	private static final String OPENID = "otuUIwwrKgYPWIngIkHkiDxfdmSQ";
	private static final String TEMPLATEID = "tRHEbmEZPTkkL_yxJErieCv3hlTr9syK1bzE38H0mnc";
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
		if (wxService.sendTemplate(OPENID, TEMPLATEID, "https://www.lovesher.com/common/",
				new String[] { "公众号【" + this.getObj().getToUserName() + "】有新消息了", this.getObj().getFromUserName(),
						this.getObj().getContent(), format.format(this.getObj().getCreateTime()),
						"备注昵称：" + this.getObj().getName() })) {
			logger.info("SUCCEES==>" + this.getObj().getId());
			wxText2.setSendStatus(WxTextSendStatusEnum.SUCCESS.getStatus());
			wxTextMapper.update(wxText2);
		} else {
			logger.info("FAILED==>" + this.getObj().getId());
			wxText2.setSendStatus(WxTextSendStatusEnum.FAIL.getStatus());
			wxTextMapper.update(wxText2);
		}
	}

	@Override
	public String getLogName() {
		return "ProducerWxText";
	}
}
