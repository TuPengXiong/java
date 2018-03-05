package com.lovesher.blog.resposity.dao.impl;

import com.lovesher.blog.resposity.bean.ArticleEffect;
import com.lovesher.blog.resposity.mapper.ArticleEffectMapper;
import com.lovesher.blog.resposity.dao.ArticleEffectDao;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;


/**
 * 
 * ArticleEffectDaoImpl
 * 
 **/
@Service("articleEffectDao")
public class ArticleEffectDaoImpl implements ArticleEffectDao{

	@Resource
	 private ArticleEffectMapper articleEffectMapper;

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	@Override
	 public ArticleEffect  selectById (Long id ){
		 return this.articleEffectMapper.selectById(id);
	}

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	@Override
	public int deleteById (Long id ){
		 return this.articleEffectMapper.deleteById(id);
	}
	/**
	 * 
	 * 添加
	 * 
	 **/
	@Override
	public long insert(ArticleEffect record ){
		 return this.articleEffectMapper.insert(record);
	}
	/**
	 * 
	 * 修改
	 * 
	 **/
	@Override
	public int update (ArticleEffect record ){
		 return this.articleEffectMapper.update(record);
	}
}