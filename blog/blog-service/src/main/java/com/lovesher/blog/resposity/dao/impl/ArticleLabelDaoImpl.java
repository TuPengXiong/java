package com.lovesher.blog.resposity.dao.impl;

import com.lovesher.blog.resposity.bean.ArticleLabel;
import com.lovesher.blog.resposity.mapper.ArticleLabelMapper;
import com.lovesher.blog.resposity.dao.ArticleLabelDao;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;


/**
 * 
 * ArticleLabelDaoImpl
 * 
 **/
@Service("articleLabelDao")
public class ArticleLabelDaoImpl implements ArticleLabelDao{

	@Resource
	 private ArticleLabelMapper articleLabelMapper;

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	@Override
	 public ArticleLabel  selectById (Long id ){
		 return this.articleLabelMapper.selectById(id);
	}

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	@Override
	public int deleteById (Long id ){
		 return this.articleLabelMapper.deleteById(id);
	}
	/**
	 * 
	 * 添加
	 * 
	 **/
	@Override
	public long insert(ArticleLabel record ){
		 return this.articleLabelMapper.insert(record);
	}
	/**
	 * 
	 * 修改
	 * 
	 **/
	@Override
	public int update (ArticleLabel record ){
		 return this.articleLabelMapper.update(record);
	}
}