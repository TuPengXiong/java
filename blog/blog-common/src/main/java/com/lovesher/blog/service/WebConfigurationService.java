package com.lovesher.blog.service;

import com.lovesher.blog.bean.WebConfigurationDTO;


/**
 * 
 * WebConfigurationService
 * 
 **/

public interface WebConfigurationService{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	WebConfigurationDTO selectById (Long id );

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
	long insert(WebConfigurationDTO record );

	/**
	 * 
	 * 修改
	 * 
	 **/
	int update (WebConfigurationDTO record );

}