/** 
 * Project Name:zookeeper 
 * File Name:ZkClientFatctory.java 
 * Package Name:com.lovesher.zookeeper.client 
 * Date:2018年4月26日下午1:29:21 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.lovesher.zookeeper.client;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import com.lovesher.zookeeper.callback.ZkDataCallback;
import com.lovesher.zookeeper.watch.ZkWatcher;

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
public class ZkClientBuilderTest {

	private static final Logger LOGGER = Logger.getLogger(ZkClientBuilderTest.class);
	
	public static void main(String[] args) {
		ZkWatcher zkWatcher = new ZkWatcher();
		ZkDataCallback zkDataCallback = new ZkDataCallback();
		ZooKeeper zk = null;
		try{
			zk = ZkClientBuilder.getZkClientBuilder().watcher(zkWatcher).connectString("127.0.0.1:2181").sessionTimeout(10000).build();
			//未建立连接进行节点操作完成会报 KeeperErrorCode = ConnectionLoss error
			ZkWatcher.waitAvailable(1000);
			LOGGER.info("zk getSessionId:"+zk.getSessionId());
			/**
			 * 节点
			 * 
			 * 1.ACL 访问控制
			 * 1.1 Id (scheme+id) 登录信息
			 * 1.2 Perms (READ | WRITE | CREATE | DELETE | ADMIN) 读写权限
			 * 1.3 opNames 具体操作   "notification", "create","delete", "exists", "getData", "setData", "getACL", "setACL","getChildren", "getChildren2", "getMaxChildren", "setMaxChildren", "ping"
			 * 
			 * 2.创建模式
			 * 2.1 CreateMode.PERSISTENT  持久化 客户端断开存在服务器
			 * 2.2 CreateMode.PERSISTENT_SEQUENTIAL  持久化并且队列 客户端断开存在服务器
			 * 2.3 CreateMode.EPHEMERAL 非持久化 客户端断开则删除
			 * 2.4 CreateMode.EPHEMERAL_SEQUENTIAL 非持久化队列 客户端断开则删除
			 * 
			 * 
			 * 组合：
			 * 1.Ids.OPEN_ACL_UNSAFE
			 *  Id:anyone 任何客户端可以访问操作
			 * 	Perms:READ | WRITE | CREATE | DELETE | ADMIN
			 *  opNames 具体操作   "notification", "create","delete", "exists", "getData", "setData", "getACL", "setACL","getChildren", "getChildren2", "getMaxChildren", "setMaxChildren", "ping"
			 * 2.Ids.CREATOR_ALL_ACL
			 *  Id:auth 创建者登录客户端才可以访问操作
			 * 	Perms:READ | WRITE | CREATE | DELETE | ADMIN
			 *  opNames 具体操作   "notification", "create","delete", "exists", "getData", "setData", "getACL", "setACL","getChildren", "getChildren2", "getMaxChildren", "setMaxChildren", "ping"
			 * 3.Ids.READ_ACL_UNSAFE
			 *  Id:read 登录客户端
			 * 	Perms:READ
			 *  opNames 具体操作   "exists", "setData", "getACL", "setACL","getChildren", "getChildren2", "getMaxChildren", "ping"
			 * 
			 */
			
			/**非持久化节点**/
			LOGGER.info("zk create path /open_acl_unsafe_ephemeral:"+zk.create("/open_acl_unsafe_ephemeral", "open_acl_unsafe_ephemeral".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL));
			LOGGER.info("zk create path /open_acl_unsafe_ephemeral_sequential:"+zk.create("/open_acl_unsafe_ephemeral_sequential", "open_acl_unsafe_ephemeral_sequential".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL));
			
			/**持久化节点**/
			if(zk.exists("/open_acl_unsafe_persistent", true) == null){
				LOGGER.info("zk create path /open_acl_unsafe_persistent:"+zk.create("/open_acl_unsafe_persistent", "open_acl_unsafe_persistent".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT));
			}
			if(zk.exists("/open_acl_unsafe_persistent_sequential", true) == null){
				LOGGER.info("zk create path /open_acl_unsafe_persistent_sequential:"+zk.create("/open_acl_unsafe_persistent_sequential", "open_acl_unsafe_persistent_sequential".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL));
			}
			if(zk.exists("/read_acl_unsafe_persistent", true) == null){
				LOGGER.info("zk create path /read_acl_unsafe_persistent:"+ zk.create("/read_acl_unsafe_persistent", "read_acl_unsafe_persistent".getBytes(), Ids.READ_ACL_UNSAFE, CreateMode.PERSISTENT));
			}
			/**持久化节点获取数据**/
			LOGGER.info("持久化节点 获取数据 zk getData /open_acl_unsafe_persistent" + new String(zk.getData("/open_acl_unsafe_persistent", zkWatcher, null)));
			/**持久化节点异步获取数据**/
			zk.getData("/open_acl_unsafe_persistent", zkWatcher, zkDataCallback,"[ctx~~open_acl_unsafe_persistent|test]");
			/**持久化节点设置数据**/
			/**
			 * BadVersion for /open_acl_unsafe_persistent
			 */
			LOGGER.info("持久化节点设置数据 open_acl_unsafe_persistent getData:"+ zk.setData("/open_acl_unsafe_persistent", "open_acl_unsafe_persistent_test".getBytes(), 1));
			
			/**可读节点获取数据**/
			LOGGER.info("持久化节点可读权限获取数据 zk getData /read_acl_unsafe_persistent" + new String(zk.getData("/read_acl_unsafe_persistent", zkWatcher, null)));
			try{
				/**
				 * NoAuth for /read_acl_unsafe_persistent  只可以读
				 **/
				LOGGER.info("持久化节点可读权限设置数据 read_acl_unsafe_persistent setData:"+zk.setData("/read_acl_unsafe_persistent", "read_acl_unsafe_persistent_set_data".getBytes(), 1));
			}catch(Exception e){
				LOGGER.error("持久化节点可读权限设置数据 报错",e);
			}
			
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
		}finally {
			ZkClientBuilder.close(zk);
		}
		
	}
}
