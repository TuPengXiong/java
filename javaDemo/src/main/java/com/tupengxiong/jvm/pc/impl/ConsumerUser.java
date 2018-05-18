/** 
 * Project Name:jvm 
 * File Name:Consumer.java 
 * Package Name:com.tupengxiong.jvm.pc 
 * Date:2017年11月3日下午2:24:20 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.tupengxiong.jvm.pc.impl;

import com.alibaba.fastjson.JSON;
import com.tupengxiong.jvm.beans.User;
import com.tupengxiong.jvm.pc.Consumer;
import com.tupengxiong.jvm.pc.Producer;

/** 
 * ClassName:Consumer <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2017年11月3日 下午2:24:20 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public  class ConsumerUser extends Consumer<User>{

	public ConsumerUser(User obj, Producer<User> producer) {
		super(obj, producer);
	}

	@Override
	public  void cosumer(){
		System.out.println(JSON.toJSONString(this.getObj()));
	}
}
  