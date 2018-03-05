package com.lovesher.blog.service.impl;

import com.lovesher.blog.bean.UserDTO;
import com.lovesher.blog.resposity.dao.UserDao;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.lovesher.blog.service.UserService;
import org.springframework.beans.BeanUtils;
import com.lovesher.blog.resposity.bean.User;


/**
 * 
 * UserService
 * 
 **/
@Service("userService")
public class UserServiceImpl implements UserService{

	@Resource
	 private UserDao userDao;

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	@Override
	public UserDTO selectById(Long id){
		UserDTO userDTO = new UserDTO();
		User user = this.userDao.selectById(id);
		if(null != user){
			BeanUtils.copyProperties(user,userDTO);
			return userDTO;
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
		return this.userDao.deleteById(id);
	}
	/**
	 * 
	 * 添加
	 * 
	 **/
	@Override
	public long insert(UserDTO record){
		User user = new User();
		if(null != record){
			BeanUtils.copyProperties(record, user);
			return this.userDao.insert(user);
		}
		return 0;
	}
	/**
	 * 
	 * 修改
	 * 
	 **/
	@Override
	public int update(UserDTO record){
		User user = new User();
		if(null != record){
			BeanUtils.copyProperties(record, user);
			return this.userDao.update(user);
		}
		return 0;
	}
}