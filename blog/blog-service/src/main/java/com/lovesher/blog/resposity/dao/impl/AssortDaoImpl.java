package com.lovesher.blog.resposity.dao.impl;

import com.lovesher.blog.resposity.bean.Assort;
import com.lovesher.blog.resposity.mapper.AssortMapper;
import com.lovesher.blog.resposity.dao.AssortDao;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;


/**
 * 
 * AssortDaoImpl
 * 
 **/
@Service("assortDao")
public class AssortDaoImpl implements AssortDao{

	@Resource
	 private AssortMapper assortMapper;

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	@Override
	 public Assort  selectById (Long id ){
		 return this.assortMapper.selectById(id);
	}

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	@Override
	public int deleteById (Long id ){
		 return this.assortMapper.deleteById(id);
	}
	/**
	 * 
	 * 添加
	 * 
	 **/
	@Override
	public long insert(Assort record ){
		 return this.assortMapper.insert(record);
	}
	/**
	 * 
	 * 修改
	 * 
	 **/
	@Override
	public int update (Assort record ){
		 return this.assortMapper.update(record);
	}
}