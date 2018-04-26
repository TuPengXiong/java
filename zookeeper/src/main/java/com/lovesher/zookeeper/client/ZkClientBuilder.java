/** 
 * Project Name:zookeeper 
 * File Name:ZkClientFatctory.java 
 * Package Name:com.lovesher.zookeeper.client 
 * Date:2018年4月26日下午1:29:21 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.lovesher.zookeeper.client;

import java.io.IOException;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * ClassName:ZkClientFatctory <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年4月26日 下午1:29:21 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class ZkClientBuilder {

	private static ZookeeperParams zookeeperParams;

	private ZkClientBuilder() {

	}

	public static ZookeeperParams getZkClientBuilder() {
		synchronized (ZkClientBuilder.class) {
			if (null == zookeeperParams) {
				zookeeperParams = new ZookeeperParams();
			}
		}
		return zookeeperParams;
	}
	
	public static ZookeeperParams newZkClientBuilder() {
		return new ZookeeperParams();
	}

	static class ZookeeperParams {

		private String connectString = "127.0.0.1:2181";
		private int sessionTimeout = 10000;
		private Watcher watcher;
		private Long sessionId;
		private byte[] sessionPasswd;
		private boolean canBeReadOnly;
		private ZooKeeper zooKeeper;

		private ZookeeperParams() {

		}

		public ZookeeperParams connectString(String connectString) {
			this.connectString = connectString;
			return this;
		}

		public ZookeeperParams sessionTimeout(int sessionTimeout) {
			this.sessionTimeout = sessionTimeout;
			return this;
		}

		public ZookeeperParams watcher(Watcher watcher) {
			this.watcher = watcher;
			return this;
		}

		public ZookeeperParams sessionId(long sessionId) {
			this.sessionId = sessionId;
			return this;
		}

		public ZookeeperParams sessionPasswd(byte[] sessionPasswd) {
			this.sessionPasswd = sessionPasswd;
			return this;
		}

		public ZookeeperParams canBeReadOnly(boolean canBeReadOnly) {
			this.canBeReadOnly = canBeReadOnly;
			return this;
		}

		public ZooKeeper build() {
			try {
				synchronized (ZookeeperParams.class) {
					if (null == zooKeeper) {
						if(null != sessionId && null != sessionPasswd){
							zooKeeper = new ZooKeeper(connectString, sessionTimeout, watcher, sessionId, sessionPasswd, canBeReadOnly);
						}else{
							zooKeeper = new ZooKeeper(connectString, sessionTimeout, watcher, canBeReadOnly);
						}
					}
				}
				return zooKeeper;
			} catch (IOException e) {
				// ignore
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
	
	public static void close(ZooKeeper zooKeeper) {
		try {
			if (null != zooKeeper) {
				zooKeeper.close();
			}
		} catch (InterruptedException e) {
			// ignore
			throw new RuntimeException(e);
		}
	}
}
