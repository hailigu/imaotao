package com.codeReading.busi.dpn.oauth;

import org.scribe.builder.ServiceBuilder;

import com.codeReading.busi.service.oauth.impl.GithubOAuthService;
import com.codeReading.busi.service.oauth.impl.OAuthServiceDeractor;
import com.codeReading.busi.service.oauth.impl.QQOAuthService;
import com.codeReading.busi.service.oauth.impl.WeixinOAuthService;
import com.codeReading.config.OAuthTypes;

public class OAuthUrlHelper implements IOAuthUrlHelper{
	private ServiceBuilder serviceBuilder;
	private String callbackUrl;
	private String oAuthType;
	
	
	public OAuthUrlHelper(ServiceBuilder serviceBuilder, String callbackUrl, String oAuthType){
		this.serviceBuilder = serviceBuilder;
		this.callbackUrl = callbackUrl;
		this.oAuthType = oAuthType;
	}
	
	@Override
	public String  getAuthorizationUrl(String preUrl){
		OAuthServiceDeractor oAuthServiceDeractor = null;
		String callbackStr = String.format(callbackUrl, oAuthType, preUrl);
		if(OAuthTypes.GITHUB.equals(oAuthType)){
			oAuthServiceDeractor = new GithubOAuthService(serviceBuilder.callback(callbackStr).build());
		}else if (OAuthTypes.QQ.equals(oAuthType)){
			oAuthServiceDeractor = new QQOAuthService(serviceBuilder.callback(callbackStr).build());
		}else if (OAuthTypes.WEIXIN.equals(oAuthType)){
			oAuthServiceDeractor = new WeixinOAuthService(serviceBuilder.callback(callbackStr).build());
		}else{
			oAuthServiceDeractor = (OAuthServiceDeractor)serviceBuilder.callback(callbackStr).build();
		}
		return oAuthServiceDeractor.getAuthorizationUrl();
	}
}
