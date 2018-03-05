package com.lovesher.blog.bean;
import java.io.Serializable;


/**
 * 
 * 
 * 
 **/
@SuppressWarnings("serial")
public class CommentDTO implements Serializable {

	/****/
	private java.lang.Integer id;

	/**评论的内容**/
	private java.lang.String articleComment;

	/**创建时间**/
	private java.util.Date createTime;

	/****/
	private java.lang.Integer articleId;

	/****/
	private java.lang.Integer userId;

	/**评论标志**/
	private java.lang.Integer parentStatus;

	/**回复评论id**/
	private java.lang.Integer parentId;



	public void setId(java.lang.Integer id){
		this.id = id;
	}

	public java.lang.Integer getId(){
		return this.id;
	}

	public void setArticleComment(java.lang.String articleComment){
		this.articleComment = articleComment;
	}

	public java.lang.String getArticleComment(){
		return this.articleComment;
	}

	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	public void setArticleId(java.lang.Integer articleId){
		this.articleId = articleId;
	}

	public java.lang.Integer getArticleId(){
		return this.articleId;
	}

	public void setUserId(java.lang.Integer userId){
		this.userId = userId;
	}

	public java.lang.Integer getUserId(){
		return this.userId;
	}

	public void setParentStatus(java.lang.Integer parentStatus){
		this.parentStatus = parentStatus;
	}

	public java.lang.Integer getParentStatus(){
		return this.parentStatus;
	}

	public void setParentId(java.lang.Integer parentId){
		this.parentId = parentId;
	}

	public java.lang.Integer getParentId(){
		return this.parentId;
	}

}
