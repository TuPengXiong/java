package com.lovesher.blog.bean;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class ArticleEffectDTO implements Serializable {

	/****/
	private java.lang.Integer id;

	/**文章id**/
	private java.lang.Integer articleId;

	/**点击数**/
	private java.lang.Integer clickNum;



	public void setId(java.lang.Integer id){
		this.id = id;
	}

	public java.lang.Integer getId(){
		return this.id;
	}

	public void setArticleId(java.lang.Integer articleId){
		this.articleId = articleId;
	}

	public java.lang.Integer getArticleId(){
		return this.articleId;
	}

	public void setClickNum(java.lang.Integer clickNum){
		this.clickNum = clickNum;
	}

	public java.lang.Integer getClickNum(){
		return this.clickNum;
	}

}
