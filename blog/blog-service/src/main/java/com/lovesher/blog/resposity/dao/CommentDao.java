package com.lovesher.blog.resposity.dao;

import com.lovesher.blog.resposity.bean.Comment;


/**
 * 
 * CommentDao
 * 
 **/

public interface CommentDao{


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	Comment  selectById (Long id );

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
	long insert(Comment record );

	/**
	 * 
	 * 修改
	 * 
	 **/
	int update (Comment record );

}