package com.lovesher.blog.resposity.dao.impl;

import com.lovesher.blog.resposity.bean.User;
import com.lovesher.blog.resposity.mapper.UserMapper;
import com.lovesher.blog.resposity.dao.UserDao;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;


/**
 * 
 * UserDaoImpl
 * 
 **/
@Service("userDao")
public class UserDaoImpl implements UserDao{

	@Resource
	 private UserMapper userMapper;

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	@Override
	 public User  selectById (Long id ){
		 return this.userMapper.selectById(id);
	}

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	@Override
	public int deleteById (Long id ){
		 return this.userMapper.deleteById(id);
	}
	/**
	 * 
	 * 添加
	 * 
	 **/
	@Override
	public long insert(User record ){
		 return this.userMapper.insert(record);
	}
	/**
	 * 
	 * 修改
	 * 
	 **/
	@Override
	public int update (User record ){
		 return this.userMapper.update(record);
	}
}