package com.codeReading.busi.service.oauth.impl;

import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.codeReading.busi.po.OAuthUserPO;
import com.codeReading.config.OAuthTypes;
import com.codeReading.core.util.HttpUtil;


public class QQOAuthService extends OAuthServiceDeractor {
    
    private static final String PROTECTED_RESOURCE_URL = "https://graph.qq.com/oauth2.0/me";

    public QQOAuthService(OAuthService oAuthService) {
        super(oAuthService, OAuthTypes.QQ);
    }


    @Override
    public OAuthUserPO getUserOAuth(Token accessToken) {
    	
//        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
//        this.signRequest(accessToken, request);
//        Response response = request.send();
        
        
		try {
			String res = HttpUtil.getInstance().sendGet(PROTECTED_RESOURCE_URL+"?access_token="+accessToken.getToken());
			int ll = res.indexOf('{');
			int rr = res.indexOf('}');
			
			String jsonstr = res.substring(ll, rr+1);

			
	        Object result = JSON.parse(jsonstr);
	        String oauthid = (JSONPath.eval(result, "$.openid").toString());
	        
	        return getUserInfo(accessToken.getToken(),"101310078", oauthid);     
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;        
    }  

   
    public OAuthUserPO getUserInfo(String access_token, String key, String open_id){
    	
    	String nickname = "";
     	String result;
     	String avatar;

    	OAuthUserPO oAuthUser = new OAuthUserPO();
        oAuthUser.setAuthtype(getoAuthType());
        oAuthUser.setAuthid(open_id);

		String url = "https://graph.qq.com/user/get_user_info?access_token="+access_token+
				"&oauth_consumer_key="+key+"&openid="+open_id;
		try{
			result = HttpUtil.getInstance().sendGet(url);
			Object robj = JSON.parse(result);
			
	        if (!result.contains("errmsg"))
	        {
		        nickname = (JSONPath.eval(robj, "$.nickname").toString());
		        avatar = (JSONPath.eval(robj, "$.figureurl_qq_2").toString());
		        
		        oAuthUser.setNickname(nickname);
		        oAuthUser.setAvatar(avatar);	
	        }
	        
		} catch (Exception e) {
			//			
		}

        return oAuthUser;
    }   
}