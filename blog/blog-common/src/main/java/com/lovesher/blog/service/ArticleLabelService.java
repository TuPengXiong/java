package com.lovesher.blog.service;

import com.lovesher.blog.bean.ArticleLabelDTO;


/**
 * 
 * ArticleLabelService
 * 
 **/

public interface ArticleLabelService{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	ArticleLabelDTO selectById (Long id );

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
	long insert(ArticleLabelDTO record );

	/**
	 * 
	 * 修改
	 * 
	 **/
	int update (ArticleLabelDTO record );

}