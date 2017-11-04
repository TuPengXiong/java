/** 
 * Project Name:jvm 
 * File Name:Producer.java 
 * Package Name:com.tupengxiong.jvm.pc 
 * Date:2017年11月3日下午2:23:51 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.tupengxiong.weixin.task.producer;

import java.util.List;

import com.tupengxiong.weixin.bean.WxText;
import com.tupengxiong.weixin.bean.enums.WxTextSendStatusEnum;
import com.tupengxiong.weixin.bean.mapper.WxTextMapper;
import com.tupengxiong.weixin.context.SpringExt;
import com.tupengxiong.weixin.task.Consumer;
import com.tupengxiong.weixin.task.Producer;
import com.tupengxiong.weixin.task.consumer.ConsumerWxText;

/** 
 * ClassName:Producer <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2017年11月3日 下午2:23:51 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class ProducerWxText extends Producer<WxText>{
	
	public ProducerWxText(int producerNum, int consumerNum) {
		super(producerNum, consumerNum);
	}

	@Override
	public void produce() {
		WxTextMapper wxTextMapper = (WxTextMapper) SpringExt.getBean("wxTextMapper");
		List<WxText> list = wxTextMapper.selectBySendStatus(WxTextSendStatusEnum.INIT.getStatus(), 0, this.getProducerNum());
		for(int i=0;i<list.size();i++){
			WxText wxText = new WxText();
			wxText.setId(wxText.getId());
			wxText.setSendStatus(WxTextSendStatusEnum.TASK.getStatus());
			wxTextMapper.update(wxText);
			this.getQUEUE().add(list.get(i));
		}
	}

	@Override
	public Consumer<WxText> getConsumer(WxText e) {
		
		return new ConsumerWxText(e, this);
	}

}
  