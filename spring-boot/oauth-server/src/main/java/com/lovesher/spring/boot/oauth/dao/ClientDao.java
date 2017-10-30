package com.lovesher.spring.boot.oauth.dao;

import com.lovesher.spring.boot.oauth.beans.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDao extends JpaRepository<Client, Long> {
    Client findByClientId(String clientId);

    Page<Client> findAll(Pageable pageable);

}