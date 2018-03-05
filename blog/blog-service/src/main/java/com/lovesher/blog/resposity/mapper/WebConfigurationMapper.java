package com.lovesher.blog.resposity.mapper;

import com.lovesher.blog.resposity.bean.WebConfiguration;


/**
 * 
 * WebConfigurationMapper数据库操作接口类
 * 
 **/

public interface WebConfigurationMapper{


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