package com.lovesher.blog.bean;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class ArticleDTO implements Serializable {

	/****/
	private java.lang.Integer id;

	/**标题**/
	private java.lang.String title;

	/**文章来源**/
	private java.lang.String articleText;

	/**文章来源**/
	private java.lang.String articleSource;

	/**用户id**/
	private java.lang.Integer userId;

	/**是否公开 0：公开 1：私密**/
	private java.lang.Integer publicState;

	/**置顶标志 0：正常 1：置顶**/
	private java.lang.Integer topState;

	/**创建时间**/
	private java.util.Date createTime;

	/**更新时间**/
	private java.util.Date modifyTime;

	/**标签id**/
	private java.lang.Integer labelId;

	/**分类id**/
	private java.lang.Integer assortId;



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

	public void setArticleText(java.lang.String articleText){
		this.articleText = articleText;
	}

	public java.lang.String getArticleText(){
		return this.articleText;
	}

	public void setArticleSource(java.lang.String articleSource){
		this.articleSource = articleSource;
	}

	public java.lang.String getArticleSource(){
		return this.articleSource;
	}

	public void setUserId(java.lang.Integer userId){
		this.userId = userId;
	}

	public java.lang.Integer getUserId(){
		return this.userId;
	}

	public void setPublicState(java.lang.Integer publicState){
		this.publicState = publicState;
	}

	public java.lang.Integer getPublicState(){
		return this.publicState;
	}

	public void setTopState(java.lang.Integer topState){
		this.topState = topState;
	}

	public java.lang.Integer getTopState(){
		return this.topState;
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

	public void setLabelId(java.lang.Integer labelId){
		this.labelId = labelId;
	}

	public java.lang.Integer getLabelId(){
		return this.labelId;
	}

	public void setAssortId(java.lang.Integer assortId){
		this.assortId = assortId;
	}

	public java.lang.Integer getAssortId(){
		return this.assortId;
	}

}
