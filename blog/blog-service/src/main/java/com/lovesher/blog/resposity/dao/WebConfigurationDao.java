package com.lovesher.blog.resposity.dao;

import com.lovesher.blog.resposity.bean.WebConfiguration;


/**
 * 
 * WebConfigurationDao
 * 
 **/

public interface WebConfigurationDao{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	WebConfiguration  selectById (Long id );

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
	long insert(WebConfiguration record );

	/**
	 * 
	 * 修改
	 * 
	 **/
	int update (WebConfiguration record );

}