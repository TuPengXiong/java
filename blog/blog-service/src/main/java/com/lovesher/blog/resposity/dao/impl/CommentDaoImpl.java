package com.lovesher.blog.resposity.dao.impl;

import com.lovesher.blog.resposity.bean.Comment;
import com.lovesher.blog.resposity.mapper.CommentMapper;
import com.lovesher.blog.resposity.dao.CommentDao;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;


/**
 * 
 * CommentDaoImpl
 * 
 **/
@Service("commentDao")
public class CommentDaoImpl implements CommentDao{

	@Resource
	 private CommentMapper commentMapper;

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	@Override
	 public Comment  selectById (Long id ){
		 return this.commentMapper.selectById(id);
	}

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	@Override
	public int deleteById (Long id ){
		 return this.commentMapper.deleteById(id);
	}
	/**
	 * 
	 * 添加
	 * 
	 **/
	@Override
	public long insert(Comment record ){
		 return this.commentMapper.insert(record);
	}
	/**
	 * 
	 * 修改
	 * 
	 **/
	@Override
	public int update (Comment record ){
		 return this.commentMapper.update(record);
	}
}