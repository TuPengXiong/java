package com.lovesher.blog.service;

import com.lovesher.blog.bean.ArticleEffectDTO;


/**
 * 
 * ArticleEffectService
 * 
 **/

public interface ArticleEffectService{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	ArticleEffectDTO selectById (Long id );

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
	long insert(ArticleEffectDTO record );

	/**
	 * 
	 * 修改
	 * 
	 **/
	int update (ArticleEffectDTO record );

}