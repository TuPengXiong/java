package com.tupengxiong.shop.dao;

import com.tupengxiong.shop.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by fangjunlong on 2017/8/8.
 */
@Repository
public interface UserDao extends JpaRepository<User, Integer> {
   User findByUsername(String username);
}
