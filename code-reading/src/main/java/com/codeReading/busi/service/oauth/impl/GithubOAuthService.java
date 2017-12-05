package com.codeReading.busi.service.oauth.impl;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.codeReading.busi.po.OAuthUserPO;
import com.codeReading.config.OAuthTypes;


public class GithubOAuthService extends OAuthServiceDeractor {
    
    private static final String PROTECTED_RESOURCE_URL = "https://api.github.com/user";

    public GithubOAuthService(OAuthService oAuthService) {
        super(oAuthService, OAuthTypes.GITHUB);
    }

    @Override
    public OAuthUserPO getUserOAuth(Token accessToken) {
        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        this.signRequest(accessToken, request);
        Response response = request.send();
        OAuthUserPO oAuthUser = new OAuthUserPO();
        oAuthUser.setAuthtype(getoAuthType());
        Object result = JSON.parse(response.getBody());
        String oauthid = (JSONPath.eval(result, "$.id").toString());
        String nickname = JSONPath.eval(result, "$.login").toString();
        String avatar = JSONPath.eval(result, "$.avatar_url").toString();        
        oAuthUser.setAuthid(oauthid);
        oAuthUser.setNickname(nickname);
        oAuthUser.setAvatar(avatar);  
        return oAuthUser;
    }

}
