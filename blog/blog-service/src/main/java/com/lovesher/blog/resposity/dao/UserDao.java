package com.lovesher.blog.resposity.dao;

import com.lovesher.blog.resposity.bean.User;


/**
 * 
 * UserDao
 * 
 **/

public interface UserDao{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	User  selectById (Long id );

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
	long insert(User record );

	/**
	 * 
	 * 修改
	 * 
	 **/
	int update (User record );

}