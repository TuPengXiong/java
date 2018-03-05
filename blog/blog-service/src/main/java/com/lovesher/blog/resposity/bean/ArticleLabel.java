package com.lovesher.blog.resposity.bean;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class ArticleLabel implements Serializable {

	/****/
	private java.lang.Integer id;

	/**标签名**/
	private java.lang.String labelName;

	/****/
	private java.util.Date createTime;

	/**更新时间**/
	private java.util.Date modifyTime;

	/**用户id**/
	private java.lang.Integer userId;



	public void setId(java.lang.Integer id){
		this.id = id;
	}

	public java.lang.Integer getId(){
		return this.id;
	}

	public void setLabelName(java.lang.String labelName){
		this.labelName = labelName;
	}

	public java.lang.String getLabelName(){
		return this.labelName;
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

	public void setUserId(java.lang.Integer userId){
		this.userId = userId;
	}

	public java.lang.Integer getUserId(){
		return this.userId;
	}

}
