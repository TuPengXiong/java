package com.lovesher.blog.resposity.dao;

import com.lovesher.blog.resposity.bean.Article;


/**
 * 
 * ArticleDao
 * 
 **/

public interface ArticleDao{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Article  selectById (Long id );

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
	long insert(Article record );

	/**
	 * 
	 * 修改
	 * 
	 **/
	int update (Article record );

}