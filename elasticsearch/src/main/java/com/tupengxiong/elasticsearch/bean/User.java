package com.tupengxiong.elasticsearch.bean;

import java.util.Date;

/** 
 *
 * @author tupx
 * 
 * @date 2017年9月19日 下午4:59:44 
 *
 * @version V1.0
 */
public class User{
	
	private Long id;
	
	private String username;

	private String phone;
	
	private String password;
	
	private Date createTime;
	
	private Date modifyTime;

	public String getUsername() {
		return username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	
}
