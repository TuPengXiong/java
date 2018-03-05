package com.lovesher.blog.resposity.dao;

import com.lovesher.blog.resposity.bean.Assort;


/**
 * 
 * AssortDao
 * 
 **/

public interface AssortDao{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Assort  selectById (Long id );

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
	long insert(Assort record );

	/**
	 * 
	 * 修改
	 * 
	 **/
	int update (Assort record );

}