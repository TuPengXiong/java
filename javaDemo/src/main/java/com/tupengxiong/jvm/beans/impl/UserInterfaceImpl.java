/** 
 * Project Name:jvm 
 * File Name:UserInterce.java 
 * Package Name:com.tupengxiong.jvm.beans 
 * Date:2018年4月12日上午10:34:18 
 * Copyright (c) 2018, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.tupengxiong.jvm.beans.impl;

import com.tupengxiong.jvm.beans.UserInterface;

/** 
 * ClassName:UserInterce <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年4月12日 上午10:34:18 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class UserInterfaceImpl implements UserInterface{

	public String getUserName(Long id) {
		return id.hashCode()+"";
	}
	
}
  