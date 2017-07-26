/** 
 * Project Name:weixin 
 * File Name:AccessTokenTask.java 
 * Package Name:com.tupengxiong.weixin.task 
 * Date:2017年6月14日下午2:17:13 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.weixin.task;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tupengxiong.weixin.service.WxService;

/**
 * ClassName:AccessTokenTask <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月14日 下午2:17:13 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
@Lazy(false)
@Component
public class AccessTokenTask implements InitializingBean {

	public final static String appId = "wx23d70d36f886f949";
	public final static String appSecret = "d4624c36b6795d1d99dcf0547af5443d";

	private static final Logger logger = Logger.getLogger(AccessTokenTask.class);
	@Resource
	private WxService wxService;

	/**
	 * 更新accessToken updateAccessToken:(更新accessToken). <br/>
	 * 一个小时刷新一次
	 * 
	 * @author tupengxiong
	 * @since JDK 1.7
	 */
	@Scheduled(cron="0 0 0/1  * * ? ")   //每1小时秒执行一次
	public void updateAccessToken() {
		Date date = new Date();
		//logger.info(new StringBuilder().append("AccessTokenTask updateAccessToken starting-------"));
		wxService.getAccessToken(appId, appSecret, true);
		logger.info(new StringBuilder().append("AccessTokenTask updateAccessToken end ").append("waster ")
				.append(new Date().getTime() - date.getTime()).append("ms-------"));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//wxService.getAccessToken(appId, appSecret, true);
		logger.info(new StringBuilder().append("AccessTokenTask InitializingBean starting-------"));
		wxService.getAccessToken(appId, appSecret, true);
	}
}
