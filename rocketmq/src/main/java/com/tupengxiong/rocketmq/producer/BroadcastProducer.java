/** 
 * Project Name:rocketmq 
 * File Name:Snippet.java 
 * Package Name:com.tupengxiong.rocketmq.producer 
 * Date:2017年9月30日下午2:08:38 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.rocketmq.producer;

import java.io.UnsupportedEncodingException;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

/**
 * ClassName:BroadcastProducer <br/>
 * Function: 广播. <br/>
 * Reason: 广播. <br/>
 * Date: 2017年9月30日 下午2:08:38 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.7
 * @see
 */

public class BroadcastProducer {
	public static void main(String[] args) {
		DefaultMQProducer producer = new DefaultMQProducer("BroadcastProducerGroup");
		producer.setNamesrvAddr("127.0.0.1:9876");
		producer.setInstanceName("BroadcastProducerGroup");
		try {
			producer.start();
			for (int i = 0; i < 100; i++) {
				Message msg = new Message("TopicTest", "TagA", "OrderID188",
						("Hello world"+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
				SendResult sendResult = producer.send(msg);
				System.out.printf("%s%n", sendResult);
			}
		} catch (MQClientException e) {

			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (RemotingException e) {

			e.printStackTrace();
		} catch (MQBrokerException e) {

			e.printStackTrace();
		} catch (InterruptedException e) {

			e.printStackTrace();
		} finally {
			producer.shutdown();
		}
	}
}
