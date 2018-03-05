package com.lovesher.blog.resposity.mapper;

import com.lovesher.blog.resposity.bean.Article;


/**
 * 
 * ArticleMapper数据库操作接口类
 * 
 **/

public interface ArticleMapper{


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