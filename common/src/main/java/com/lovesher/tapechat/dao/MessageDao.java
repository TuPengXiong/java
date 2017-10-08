package com.lovesher.tapechat.dao;

import com.lovesher.tapechat.beans.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tpx on 2017/10/8.
 */
@Repository
public interface MessageDao extends JpaRepository<Message, Long> {

    public Page<Message> findAll(Pageable pageable);
}
