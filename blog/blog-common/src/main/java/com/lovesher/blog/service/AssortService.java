package com.lovesher.blog.service;

import com.lovesher.blog.bean.AssortDTO;


/**
 * 
 * AssortService
 * 
 **/

public interface AssortService{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	AssortDTO selectById (Long id );

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
	long insert(AssortDTO record );

	/**
	 * 
	 * 修改
	 * 
	 **/
	int update (AssortDTO record );

}