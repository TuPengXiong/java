package com.lovesher.blog.service.impl;

import com.lovesher.blog.bean.WebConfigurationDTO;
import com.lovesher.blog.resposity.dao.WebConfigurationDao;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lovesher.blog.service.WebConfigurationService;
import org.springframework.beans.BeanUtils;
import com.lovesher.blog.resposity.bean.WebConfiguration;


/**
 * 
 * WebConfigurationService
 * 
 **/
@Service("webConfigurationService")
public class WebConfigurationServiceImpl implements WebConfigurationService{

	@Resource
	 private WebConfigurationDao webConfigurationDao;

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	@Override
	public WebConfigurationDTO selectById(Long id){
		WebConfigurationDTO webConfigurationDTO = new WebConfigurationDTO();
		WebConfiguration webConfiguration = this.webConfigurationDao.selectById(id);
		if(null != webConfiguration){
			BeanUtils.copyProperties(webConfiguration,webConfigurationDTO);
			return webConfigurationDTO;
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
		return this.webConfigurationDao.deleteById(id);
	}
	/**
	 * 
	 * 添加
	 * 
	 **/
	@Override
	public long insert(WebConfigurationDTO record){
		WebConfiguration webConfiguration = new WebConfiguration();
		if(null != record){
			BeanUtils.copyProperties(record, webConfiguration);
			return this.webConfigurationDao.insert(webConfiguration);
		}
		return 0;
	}
	/**
	 * 
	 * 修改
	 * 
	 **/
	@Override
	public int update(WebConfigurationDTO record){
		WebConfiguration webConfiguration = new WebConfiguration();
		if(null != record){
			BeanUtils.copyProperties(record, webConfiguration);
			return this.webConfigurationDao.update(webConfiguration);
		}
		return 0;
	}
}