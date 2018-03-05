package com.lovesher.blog.service;

import com.lovesher.blog.bean.ArticleDTO;


/**
 * 
 * ArticleService
 * 
 **/

public interface ArticleService{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	ArticleDTO selectById (Long id );

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
	long insert(ArticleDTO record );

	/**
	 * 
	 * 修改
	 * 
	 **/
	int update (ArticleDTO record );

}