package com.lovesher.blog.service;

import com.lovesher.blog.bean.UserDTO;


/**
 * 
 * UserService
 * 
 **/

public interface UserService{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	UserDTO selectById (Long id );

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
	long insert(UserDTO record );

	/**
	 * 
	 * 修改
	 * 
	 **/
	int update (UserDTO record );

}