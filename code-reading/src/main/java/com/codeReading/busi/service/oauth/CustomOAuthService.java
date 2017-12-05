package com.codeReading.busi.service.oauth;

import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import com.codeReading.busi.po.OAuthUserPO;

public interface CustomOAuthService extends OAuthService {
	
    String getoAuthType();
    String getAuthorizationUrl();
    OAuthUserPO getUserOAuth(Token accessToken);    
}
