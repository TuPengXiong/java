package com.lovesher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import com.lovesher.beans.Client;
import com.lovesher.dao.ClientDao;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tpx on 2017/10/24.
 */
@Service("dtClientDetailsService")
public class DtClientDetailsService implements ClientDetailsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private ClientDao clientDao;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        logger.info("clientId=========:"+clientId);
        Client client = clientDao.findByClientId(clientId);
        if(null == client){
            throw new ClientRegistrationException("not exist!!!");
        }
        BaseClientDetails clientDetails = new BaseClientDetails(client.getClientId(), client.getResourceIds(), client.getScopes(), client.getAuthorizedGrantTypes(), client.getAuthorities(), client.getRegisteredRedirectUri());
        List<String> setAutoApproveScopes = new ArrayList<String>() ;
        setAutoApproveScopes.add("test");
        clientDetails.setAutoApproveScopes(setAutoApproveScopes);
        return clientDetails;
    }
}
