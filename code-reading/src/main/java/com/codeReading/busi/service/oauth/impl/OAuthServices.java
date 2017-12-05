package com.codeReading.busi.service.oauth.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OAuthServices {
    
    @Autowired List<OAuthServiceDeractor> oAuthServiceDeractors;
    
    public OAuthServiceDeractor getOAuthService(String type){
    	OAuthServiceDeractor oAuthService = null;
        
        Iterator<OAuthServiceDeractor> it = oAuthServiceDeractors.iterator();
        while( it.hasNext() ) {
        	OAuthServiceDeractor oAuthServiceDeractor = it.next();
          if(oAuthServiceDeractor.getoAuthType().equals(type)){
        	  oAuthService =  oAuthServiceDeractor;
        	  break;
          }
        }       
        
        return oAuthService;
    }
    
    public List<OAuthServiceDeractor> getAllOAuthServices(){
        return oAuthServiceDeractors;
    }

}
