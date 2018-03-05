package com.lovesher.blog.service.impl;

import com.lovesher.blog.bean.ArticleLabelDTO;
import com.lovesher.blog.resposity.dao.ArticleLabelDao;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lovesher.blog.service.ArticleLabelService;
import org.springframework.beans.BeanUtils;
import com.lovesher.blog.resposity.bean.ArticleLabel;


/**
 * 
 * ArticleLabelService
 * 
 **/
@Service("articleLabelService")
public class ArticleLabelServiceImpl implements ArticleLabelService{

	@Resource
	 private ArticleLabelDao articleLabelDao;

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	@Override
	public ArticleLabelDTO selectById(Long id){
		ArticleLabelDTO articleLabelDTO = new ArticleLabelDTO();
		ArticleLabel articleLabel = this.articleLabelDao.selectById(id);
		if(null != articleLabel){
			BeanUtils.copyProperties(articleLabel,articleLabelDTO);
			return articleLabelDTO;
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
		return this.articleLabelDao.deleteById(id);
	}
	/**
	 * 
	 * 添加
	 * 
	 **/
	@Override
	public long insert(ArticleLabelDTO record){
		ArticleLabel articleLabel = new ArticleLabel();
		if(null != record){
			BeanUtils.copyProperties(record, articleLabel);
			return this.articleLabelDao.insert(articleLabel);
		}
		return 0;
	}
	/**
	 * 
	 * 修改
	 * 
	 **/
	@Override
	public int update(ArticleLabelDTO record){
		ArticleLabel articleLabel = new ArticleLabel();
		if(null != record){
			BeanUtils.copyProperties(record, articleLabel);
			return this.articleLabelDao.update(articleLabel);
		}
		return 0;
	}
}