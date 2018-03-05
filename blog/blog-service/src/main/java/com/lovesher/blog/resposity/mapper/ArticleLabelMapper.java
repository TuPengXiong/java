package com.lovesher.blog.resposity.mapper;

import com.lovesher.blog.resposity.bean.ArticleLabel;


/**
 * 
 * ArticleLabelMapper数据库操作接口类
 * 
 **/

public interface ArticleLabelMapper{


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