/** 
 * Project Name:spring-boot-first 
 * File Name:UserMapper.java 
 * Package Name:com.tupengxiong.spring_boot_first.mapper 
 * Date:2017年6月28日上午11:24:38 
 * Copyright (c) 2017, tupengxiong@qq.com All Rights Reserved. 
 * 
*/

package com.tupengxiong.spring_boot_first.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.tupengxiong.spring_boot_first.repository.User;

/**
 * ClassName:UserMapper <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年6月28日 上午11:24:38 <br/>
 * 
 * @author tupengxiong
 * @version
 * @since JDK 1.8
 * @see
 */
@Mapper
public interface UserMapper {
	@Select("select * from user where name = #{name}")
	User findUserByName(@Param("name") String name);
	
	@Insert("insert into user(name,age) values (#{name},#{age})")
	int insert(User user);

}
