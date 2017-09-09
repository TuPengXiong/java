package com.tupengxiong.common.dao;

import com.tupengxiong.common.beans.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tpx on 2017/8/8.
 */
@Repository
public interface UserDao extends JpaRepository<User, Integer> {
   User findByUsername(String username);

   Page<User> findAll(Pageable pageable);

}
