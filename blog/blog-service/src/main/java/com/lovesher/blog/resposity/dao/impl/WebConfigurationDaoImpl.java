package com.lovesher.blog.resposity.dao.impl;

import com.lovesher.blog.resposity.bean.WebConfiguration;
import com.lovesher.blog.resposity.mapper.WebConfigurationMapper;
import com.lovesher.blog.resposity.dao.WebConfigurationDao;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;


/**
 * 
 * WebConfigurationDaoImpl
 * 
 **/
@Service("webConfigurationDao")
public class WebConfigurationDaoImpl implements WebConfigurationDao{

	@Resource
	 private WebConfigurationMapper webConfigurationMapper;

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	@Override
	 public WebConfiguration  selectById (Long id ){
		 return this.webConfigurationMapper.selectById(id);
	}

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	@Override
	public int deleteById (Long id ){
		 return this.webConfigurationMapper.deleteById(id);
	}
	/**
	 * 
	 * 添加
	 * 
	 **/
	@Override
	public long insert(WebConfiguration record ){
		 return this.webConfigurationMapper.insert(record);
	}
	/**
	 * 
	 * 修改
	 * 
	 **/
	@Override
	public int update (WebConfiguration record ){
		 return this.webConfigurationMapper.update(record);
	}
}