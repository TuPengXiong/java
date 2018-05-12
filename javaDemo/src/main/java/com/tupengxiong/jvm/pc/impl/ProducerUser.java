/** 
 * Project Name:jvm 
 * File Name:Producer.java 
 * Package Name:com.tupengxiong.jvm.pc 
 * Date:2017年11月3日下午2:23:51 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.tupengxiong.jvm.pc.impl;

import java.util.Date;

import com.tupengxiong.jvm.beans.User;
import com.tupengxiong.jvm.pc.Consumer;
import com.tupengxiong.jvm.pc.Producer;

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
public class ProducerUser extends Producer<User>{

	public ProducerUser(int producerNum, int consumerNum) {
		super(producerNum, consumerNum);
	}

	Long i = 0L;
	@Override
	public void produce() {
		for(Long j=0L;j<this.getProducerNum();j++){
			User user = new User();
			user.setId(i++);
			user.setCreateTime(new Date());
			this.getQUEUE().add(user);
		}
	}

	@Override
	public Consumer<User> getConsumer(User e) {
		
		return new ConsumerUser(e, this);
	}

}
  