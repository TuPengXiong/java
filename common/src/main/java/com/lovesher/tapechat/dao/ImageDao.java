package com.lovesher.tapechat.dao;

import com.lovesher.tapechat.beans.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tpx on 2017/8/8.
 */
@Repository
public interface ImageDao extends JpaRepository<Image, Long> {
   Image findByKey(String key);

   Page<Image> findAll(Pageable pageable);

}
