package com.lovesher.blog.service.impl;

import com.lovesher.blog.bean.AssortDTO;
import com.lovesher.blog.resposity.dao.AssortDao;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lovesher.blog.service.AssortService;
import org.springframework.beans.BeanUtils;
import com.lovesher.blog.resposity.bean.Assort;


/**
 * 
 * AssortService
 * 
 **/
@Service("assortService")
public class AssortServiceImpl implements AssortService{

	@Resource
	 private AssortDao assortDao;

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	@Override
	public AssortDTO selectById(Long id){
		AssortDTO assortDTO = new AssortDTO();
		Assort assort = this.assortDao.selectById(id);
		if(null != assort){
			BeanUtils.copyProperties(assort,assortDTO);
			return assortDTO;
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
		return this.assortDao.deleteById(id);
	}
	/**
	 * 
	 * 添加
	 * 
	 **/
	@Override
	public long insert(AssortDTO record){
		Assort assort = new Assort();
		if(null != record){
			BeanUtils.copyProperties(record, assort);
			return this.assortDao.insert(assort);
		}
		return 0;
	}
	/**
	 * 
	 * 修改
	 * 
	 **/
	@Override
	public int update(AssortDTO record){
		Assort assort = new Assort();
		if(null != record){
			BeanUtils.copyProperties(record, assort);
			return this.assortDao.update(assort);
		}
		return 0;
	}
}