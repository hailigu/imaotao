package com.codeReading.busi.service.oauth.impl;

import org.scribe.model.OAuthConstants;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.codeReading.busi.po.OAuthUserPO;
import com.codeReading.config.OAuthTypes;

public abstract class OAuthServiceDeractor implements OAuthService {
	
    private static final String WEIXIN_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code";

    private final OAuthService oAuthService;
    private final String oAuthType;
    private final String authorizationUrl;
    
    public OAuthServiceDeractor(OAuthService oAuthService, String type) {
        super();
        this.oAuthService = oAuthService;
        this.oAuthType = type;
        this.authorizationUrl = oAuthService.getAuthorizationUrl(null);
    }

    @Override
    public Token getRequestToken() {
        return oAuthService.getRequestToken();
    }

    @Override
    public Token getAccessToken(Token requestToken, Verifier verifier) {
    	if(oAuthType.equals(OAuthTypes.WEIXIN)){
    		return getWeixinAccessToken(requestToken, verifier);
    	}
    	else{
            return oAuthService.getAccessToken(requestToken, verifier);
    	}
    }
       
    /*写得很丑啊，就因为微信的两个参数名与其他不同，悲哀*/
    public Token getWeixinAccessToken(Token requestToken, Verifier verifier){
      OAuthRequest request = new OAuthRequest(Verb.GET, WEIXIN_ACCESS_TOKEN_URL);
      request.addQuerystringParameter("appid", "wx19d3a5db81f2b693");
      request.addQuerystringParameter("secret", "136da85e18bb8cba75a2c367bfbda3e8");
      request.addQuerystringParameter(OAuthConstants.CODE, verifier.getValue());
      request.addQuerystringParameter(OAuthConstants.SCOPE, "snsapi_login");
      Response response = request.send();
      String responceBody = response.getBody();
      Object result = JSON.parse(responceBody);
      Object robj = JSONPath.eval(result, "$.access_token");
      return new Token(robj.toString(), "", responceBody);
    }    

    @Override
    public void signRequest(Token accessToken, OAuthRequest request) {
        oAuthService.signRequest(accessToken, request);
    }

    @Override
    public String getVersion() {
        return oAuthService.getVersion();
    }

    @Override
    public String getAuthorizationUrl(Token requestToken) {
        return oAuthService.getAuthorizationUrl(requestToken);
    }

    public String getoAuthType() {
        return oAuthType;
    }
    
    public String getAuthorizationUrl(){
        return authorizationUrl;
    }
    
    public abstract OAuthUserPO getUserOAuth(Token accessToken);

}
