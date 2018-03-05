package com.lovesher.blog.resposity.dao;

import com.lovesher.blog.resposity.bean.ArticleEffect;


/**
 * 
 * ArticleEffectDao
 * 
 **/

public interface ArticleEffectDao{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	ArticleEffect  selectById (Long id );

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
	long insert(ArticleEffect record );

	/**
	 * 
	 * 修改
	 * 
	 **/
	int update (ArticleEffect record );

}