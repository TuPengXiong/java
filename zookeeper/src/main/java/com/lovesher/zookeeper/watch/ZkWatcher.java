/** 
 * Project Name:zookeeper 
 * File Name:ZkWatcher.java 
 * Package Name:com.lovesher.zookeeper.watch 
 * Date:2018年4月26日下午2:28:13 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.lovesher.zookeeper.watch;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.alibaba.fastjson.JSON;

/** 
 * ClassName:ZkWatcher <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年4月26日 下午2:28:13 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class ZkWatcher implements Watcher{

	private static final Logger LOGGER = Logger.getLogger(ZkWatcher.class);
	
	private static CountDownLatch countDownLatch = new CountDownLatch(1);
	
	public void process(WatchedEvent event) {
		if(event.getType() == Event.EventType.None && event.getState() == Event.KeeperState.SyncConnected){
			countDownLatch.countDown();
		}else if(event.getType() == Event.EventType.None){
			countDownLatch = new CountDownLatch(1); 
		}
		LOGGER.info("ZkWatcher process:"+JSON.toJSONString(event));
	}
	
	public static void waitAvailable(long timeout){
		long curr = System.currentTimeMillis();
		boolean isAvailable = Boolean.FALSE;
		while(!isAvailable){
			if(countDownLatch.getCount() == 1L){
				if(curr - System.currentTimeMillis() > timeout){
					throw new RuntimeException("zookeeper connection timeout...");
				}
				continue;
			}
			isAvailable = Boolean.TRUE;
		}
	}

}
  