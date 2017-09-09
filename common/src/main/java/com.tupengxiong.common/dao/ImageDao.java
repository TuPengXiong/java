package com.tupengxiong.common.dao;

import com.tupengxiong.common.beans.Image;
import com.tupengxiong.common.beans.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tpx on 2017/8/8.
 */
@Repository
public interface ImageDao extends JpaRepository<Image, Integer> {
   Image findByKey(String key);

   Page<Image> findAll(Pageable pageable);

}
