package com.tupengxiong.jvm.beans;

import java.util.Date;

/**
 * Hello world!
 *
 */
public class User {
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

	public User(Long id, String username, String password, Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	public User(Long id) {
		super();
		this.id = id;
	}

	public User() {
		super();
	}
}
