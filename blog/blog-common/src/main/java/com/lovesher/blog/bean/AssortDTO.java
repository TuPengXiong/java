package com.lovesher.blog.bean;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class AssortDTO implements Serializable {

	/****/
	private java.lang.Integer id;

	/****/
	private java.lang.String assortName;

	/**排序等级 **/
	private java.lang.Integer sortGrade;

	/**链接地址**/
	private java.lang.String urlLink;



	public void setId(java.lang.Integer id){
		this.id = id;
	}

	public java.lang.Integer getId(){
		return this.id;
	}

	public void setAssortName(java.lang.String assortName){
		this.assortName = assortName;
	}

	public java.lang.String getAssortName(){
		return this.assortName;
	}

	public void setSortGrade(java.lang.Integer sortGrade){
		this.sortGrade = sortGrade;
	}

	public java.lang.Integer getSortGrade(){
		return this.sortGrade;
	}

	public void setUrlLink(java.lang.String urlLink){
		this.urlLink = urlLink;
	}

	public java.lang.String getUrlLink(){
		return this.urlLink;
	}

}
