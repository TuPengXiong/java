package com.lovesher.blog.service.impl;

import com.lovesher.blog.bean.ArticleDTO;
import com.lovesher.blog.resposity.dao.ArticleDao;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lovesher.blog.service.ArticleService;
import org.springframework.beans.BeanUtils;
import com.lovesher.blog.resposity.bean.Article;


/**
 * 
 * ArticleService
 * 
 **/
@Service("articleService")
public class ArticleServiceImpl implements ArticleService{

	@Resource
	 private ArticleDao articleDao;

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	@Override
	public ArticleDTO selectById(Long id){
		ArticleDTO articleDTO = new ArticleDTO();
		Article article = this.articleDao.selectById(id);
		if(null != article){
			BeanUtils.copyProperties(article,articleDTO);
			return articleDTO;
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
		return this.articleDao.deleteById(id);
	}
	/**
	 * 
	 * 添加
	 * 
	 **/
	@Override
	public long insert(ArticleDTO record){
		Article article = new Article();
		if(null != record){
			BeanUtils.copyProperties(record, article);
			return this.articleDao.insert(article);
		}
		return 0;
	}
	/**
	 * 
	 * 修改
	 * 
	 **/
	@Override
	public int update(ArticleDTO record){
		Article article = new Article();
		if(null != record){
			BeanUtils.copyProperties(record, article);
			return this.articleDao.update(article);
		}
		return 0;
	}
}