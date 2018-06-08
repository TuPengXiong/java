package com.lovesher.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lovesher.beans.User;

/**
 * Created by tpx on 2017/8/8.
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {
   User findByUsername(String username);

   Page<User> findAll(Pageable pageable);

}
