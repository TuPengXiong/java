/** 
 * Project Name:redis 
 * File Name:User.java 
 * Package Name:com.tupengxiong.bean 
 * Date:2017年11月2日上午9:59:11 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/  
  
package com.tupengxiong.bean;

import java.io.Serializable;
import java.util.Date;

/** 
 * ClassName:User <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2017年11月2日 上午9:59:11 <br/> 
 * @author   tupengxiong 
 * @version   
 * @since    JDK 1.7
 * @see       
 */
public class User implements Serializable{

	/** 
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么). 
	 * @since JDK 1.7 
	 */  
	private static final long serialVersionUID = -2485896236056270983L;

	private Long id;
	
	private String username;
	
	private String password;
	
	private Date createTime;
	
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
}
  