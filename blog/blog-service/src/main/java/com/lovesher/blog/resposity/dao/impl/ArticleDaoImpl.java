package com.lovesher.blog.resposity.dao.impl;

import com.lovesher.blog.resposity.bean.Article;
import com.lovesher.blog.resposity.mapper.ArticleMapper;
import com.lovesher.blog.resposity.dao.ArticleDao;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;


/**
 * 
 * ArticleDaoImpl
 * 
 **/
@Service("articleDao")
public class ArticleDaoImpl implements ArticleDao{

	@Resource
	 private ArticleMapper articleMapper;

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	@Override
	 public Article  selectById (Long id ){
		 return this.articleMapper.selectById(id);
	}

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	@Override
	public int deleteById (Long id ){
		 return this.articleMapper.deleteById(id);
	}
	/**
	 * 
	 * 添加
	 * 
	 **/
	@Override
	public long insert(Article record ){
		 return this.articleMapper.insert(record);
	}
	/**
	 * 
	 * 修改
	 * 
	 **/
	@Override
	public int update (Article record ){
		 return this.articleMapper.update(record);
	}
}