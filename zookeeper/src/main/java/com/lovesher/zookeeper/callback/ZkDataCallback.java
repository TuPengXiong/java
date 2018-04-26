/** 
 * Project Name:zookeeper 
 * File Name:ZkDataCallback.java 
 * Package Name:com.lovesher.zookeeper.callback 
 * Date:2018年4月26日下午5:24:02 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.lovesher.zookeeper.callback;

import org.apache.log4j.Logger;
import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.data.Stat;

import com.alibaba.fastjson.JSON;
import com.lovesher.zookeeper.watch.ZkWatcher;

/** 
 * ClassName:ZkDataCallback <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年4月26日 下午5:24:02 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class ZkDataCallback implements DataCallback{

	private static final Logger LOGGER = Logger.getLogger(ZkWatcher.class);
	public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
		LOGGER.info("DataCallback~~" + "rc:" + rc + "|path:" + path + "|ctx:" + ctx + "|stat:" + JSON.toJSONString(stat) + "|data" + new String(data));
	}

}
  