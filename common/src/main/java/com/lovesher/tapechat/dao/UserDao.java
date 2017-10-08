package com.lovesher.tapechat.dao;

import com.lovesher.tapechat.beans.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tpx on 2017/8/8.
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {
   User findByUsername(String username);

   Page<User> findAll(Pageable pageable);

}
