/** 
 * Project Name:hadoop 
 * File Name:ConnnectionFactory.java 
 * Package Name:com.lovesher.hadoop.hbase 
 * Date:2018年5月21日下午2:08:54 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.lovesher.hadoop.hbase;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Connection;

/**
 * ClassName:ConnnectionFactory <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年5月21日 下午2:08:54 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */
public class ConnectionFactory extends org.apache.hadoop.hbase.client.ConnectionFactory {

	public static void close(Connection connection) {
		if (null != connection) {
			try {
				connection.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	public static void main(String[] args) {
		Connection connection = null;
		try {
			/**
			 * conf.addResource("hbase-default.xml");
			 * conf.addResource("hbase-site.xml");
			 */
			connection = ConnectionFactory.createConnection();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(connection);
		}

	}
}
