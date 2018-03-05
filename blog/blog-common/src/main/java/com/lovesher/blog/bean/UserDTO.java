package com.lovesher.blog.bean;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class UserDTO implements Serializable {

	/****/
	private java.lang.Integer id;

	/**用户名**/
	private java.lang.String userName;

	/**真实用户名**/
	private java.lang.String realName;

	/****/
	private java.lang.Integer phone;

	/****/
	private java.lang.String email;

	/****/
	private java.util.Date createTime;

	/****/
	private java.util.Date modifyTime;

	/**状态 0：正常 1：删除**/
	private java.lang.Integer status;



	public void setId(java.lang.Integer id){
		this.id = id;
	}

	public java.lang.Integer getId(){
		return this.id;
	}

	public void setUserName(java.lang.String userName){
		this.userName = userName;
	}

	public java.lang.String getUserName(){
		return this.userName;
	}

	public void setRealName(java.lang.String realName){
		this.realName = realName;
	}

	public java.lang.String getRealName(){
		return this.realName;
	}

	public void setPhone(java.lang.Integer phone){
		this.phone = phone;
	}

	public java.lang.Integer getPhone(){
		return this.phone;
	}

	public void setEmail(java.lang.String email){
		this.email = email;
	}

	public java.lang.String getEmail(){
		return this.email;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setModifyTime(java.util.Date modifyTime){
		this.modifyTime = modifyTime;
	}

	public java.util.Date getModifyTime(){
		return this.modifyTime;
	}

	public void setStatus(java.lang.Integer status){
		this.status = status;
	}

	public java.lang.Integer getStatus(){
		return this.status;
	}

}
