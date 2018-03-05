package com.lovesher.blog.service;

import com.lovesher.blog.bean.CommentDTO;


/**
 * 
 * CommentService
 * 
 **/

public interface CommentService{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	CommentDTO selectById (Long id );

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
	long insert(CommentDTO record );

	/**
	 * 
	 * 修改
	 * 
	 **/
	int update (CommentDTO record );

}