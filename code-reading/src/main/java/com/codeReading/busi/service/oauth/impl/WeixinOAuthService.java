package com.codeReading.busi.service.oauth.impl;

import java.util.HashMap;
import java.util.Map;

import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.codeReading.busi.po.OAuthUserPO;
import com.codeReading.config.OAuthTypes;
import com.codeReading.core.util.HttpUtil;

public class WeixinOAuthService extends OAuthServiceDeractor {

    public WeixinOAuthService(OAuthService oAuthService) {
        super(oAuthService, OAuthTypes.WEIXIN);
    }



    @Override
    public OAuthUserPO getUserOAuth(Token accessToken) {    	
        Object result = JSON.parse(accessToken.getRawResponse());
        String oauthid = (JSONPath.eval(result, "$.openid").toString());
        return getUserInfo(accessToken.getToken(), oauthid); 
    }
    

    public OAuthUserPO getUserInfo(String access_token, String open_id){
    	
    	String nickname = "";
    	String avatar = "";
     	String result;
     	
     	OAuthUserPO oAuthUser = new OAuthUserPO();
        oAuthUser.setAuthtype(getoAuthType());
        oAuthUser.setAuthid(open_id);

		Map<String, String> param = new HashMap<String, String>();
		param.put("access_token", access_token);
		param.put("openid", open_id);
		try{
			result = HttpUtil.getInstance().sendPost("https://api.weixin.qq.com/sns/userinfo", param);
			Object robj = JSON.parse(result);
			
	        if (!result.contains("errmsg"))
	        {
		        nickname = (JSONPath.eval(robj, "$.nickname").toString());
		        avatar = (JSONPath.eval(robj, "$.headimgurl").toString());
		        
		        oAuthUser.setNickname(nickname);
		        oAuthUser.setAvatar(avatar);		        
	        }	        
		} catch (Exception e) {
			//		
		}

        return oAuthUser;
    }   
}