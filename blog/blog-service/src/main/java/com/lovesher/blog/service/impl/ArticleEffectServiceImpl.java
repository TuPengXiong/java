package com.lovesher.blog.service.impl;

import com.lovesher.blog.bean.ArticleEffectDTO;
import com.lovesher.blog.resposity.dao.ArticleEffectDao;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lovesher.blog.service.ArticleEffectService;
import org.springframework.beans.BeanUtils;
import com.lovesher.blog.resposity.bean.ArticleEffect;


/**
 * 
 * ArticleEffectService
 * 
 **/
@Service("articleEffectService")
public class ArticleEffectServiceImpl implements ArticleEffectService{

	@Resource
	 private ArticleEffectDao articleEffectDao;

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	@Override
	public ArticleEffectDTO selectById(Long id){
		ArticleEffectDTO articleEffectDTO = new ArticleEffectDTO();
		ArticleEffect articleEffect = this.articleEffectDao.selectById(id);
		if(null != articleEffect){
			BeanUtils.copyProperties(articleEffect,articleEffectDTO);
			return articleEffectDTO;
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
		return this.articleEffectDao.deleteById(id);
	}
	/**
	 * 
	 * 添加
	 * 
	 **/
	@Override
	public long insert(ArticleEffectDTO record){
		ArticleEffect articleEffect = new ArticleEffect();
		if(null != record){
			BeanUtils.copyProperties(record, articleEffect);
			return this.articleEffectDao.insert(articleEffect);
		}
		return 0;
	}
	/**
	 * 
	 * 修改
	 * 
	 **/
	@Override
	public int update(ArticleEffectDTO record){
		ArticleEffect articleEffect = new ArticleEffect();
		if(null != record){
			BeanUtils.copyProperties(record, articleEffect);
			return this.articleEffectDao.update(articleEffect);
		}
		return 0;
	}
}