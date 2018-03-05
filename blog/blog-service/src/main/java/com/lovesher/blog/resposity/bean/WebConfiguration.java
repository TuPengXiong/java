package com.lovesher.blog.resposity.bean;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class WebConfiguration implements Serializable {

	/****/
	private java.lang.Integer id;

	/**网站标题**/
	private java.lang.String title;

	/**副标题**/
	private java.lang.String subtitle;

	/**状态 0：正常 1：无效**/
	private java.lang.Integer statu;



	public void setId(java.lang.Integer id){
		this.id = id;
	}

	public java.lang.Integer getId(){
		return this.id;
	}

	public void setTitle(java.lang.String title){
		this.title = title;
	}

	public java.lang.String getTitle(){
		return this.title;
	}

	public void setSubtitle(java.lang.String subtitle){
		this.subtitle = subtitle;
	}

	public java.lang.String getSubtitle(){
		return this.subtitle;
	}

	public void setStatu(java.lang.Integer statu){
		this.statu = statu;
	}

	public java.lang.Integer getStatu(){
		return this.statu;
	}

}
