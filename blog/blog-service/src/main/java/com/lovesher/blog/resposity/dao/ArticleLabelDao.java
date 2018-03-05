package com.lovesher.blog.resposity.dao;

import com.lovesher.blog.resposity.bean.ArticleLabel;


/**
 * 
 * ArticleLabelDao
 * 
 **/

public interface ArticleLabelDao{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	ArticleLabel  selectById (Long id );

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteById (Long id );

	/**
	 * 
	 * 添加
	 * 
	 **/
	long insert(ArticleLabel record );

	/**
	 * 
	 * 修改
	 * 
	 **/
	int update (ArticleLabel record );

}