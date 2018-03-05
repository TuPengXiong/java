package com.lovesher.blog.service.impl;

import com.lovesher.blog.bean.CommentDTO;
import com.lovesher.blog.resposity.dao.CommentDao;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lovesher.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import com.lovesher.blog.resposity.bean.Comment;


/**
 * 
 * CommentService
 * 
 **/
@Service("commentService")
public class CommentServiceImpl implements CommentService{

	@Resource
	 private CommentDao commentDao;

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	@Override
	public CommentDTO selectById(Long id){
		CommentDTO commentDTO = new CommentDTO();
		Comment comment = this.commentDao.selectById(id);
		if(null != comment){
			BeanUtils.copyProperties(comment,commentDTO);
			return commentDTO;
		}
		return null;
	}

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	@Override
	public int deleteById(Long id){
		return this.commentDao.deleteById(id);
	}
	/**
	 * 
	 * 添加
	 * 
	 **/
	@Override
	public long insert(CommentDTO record){
		Comment comment = new Comment();
		if(null != record){
			BeanUtils.copyProperties(record, comment);
			return this.commentDao.insert(comment);
		}
		return 0;
	}
	/**
	 * 
	 * 修改
	 * 
	 **/
	@Override
	public int update(CommentDTO record){
		Comment comment = new Comment();
		if(null != record){
			BeanUtils.copyProperties(record, comment);
			return this.commentDao.update(comment);
		}
		return 0;
	}
}