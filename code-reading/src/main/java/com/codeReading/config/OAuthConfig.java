package com.codeReading.config;

import org.scribe.builder.ServiceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.context.WebApplicationContext;

import com.codeReading.busi.dpn.oauth.IOAuthUrlHelper;
import com.codeReading.busi.dpn.oauth.OAuthUrlHelper;
import com.codeReading.busi.service.oauth.impl.GithubApi;
import com.codeReading.busi.service.oauth.impl.GithubOAuthService;
import com.codeReading.busi.service.oauth.impl.OAuthServiceDeractor;
import com.codeReading.busi.service.oauth.impl.QQApi;
import com.codeReading.busi.service.oauth.impl.QQOAuthService;
import com.codeReading.busi.service.oauth.impl.WeixinApi;
import com.codeReading.busi.service.oauth.impl.WeixinOAuthService;

@Configuration
@PropertySource(value="classpath:oauth.properties")
public class OAuthConfig {
												
    private static final String CALLBACK_URL = "http://www.imaotao.cn/oauth/%s/callback?preUrl=%s";
	//private static final String CALLBACK_URL = "http://www.kiford.com/oauth/%s/callback";
    
    @Autowired private Environment env;
    
    @Autowired private WebApplicationContext context;
    
    @Bean
    public GithubApi githubApi(){
        return new GithubApi(env.getProperty("oAuth.github.state"));
    }    

    @Bean
    public OAuthServiceDeractor getGithubOAuthService(){
    	OAuthServiceDeractor oAuthServiceDeractor= new GithubOAuthService(new ServiceBuilder()
                .provider(githubApi())
                .apiKey(env.getProperty("oAuth.github.appId"))
                .apiSecret(env.getProperty("oAuth.github.appKey"))
                .callback(String.format(CALLBACK_URL, OAuthTypes.GITHUB,""))
                .build());
    
    	context.getServletContext().setAttribute("githubAuthorizationUrl", oAuthServiceDeractor.getAuthorizationUrl());
    	return oAuthServiceDeractor;
    }

   
    @Bean
    public WeixinApi weixinApi(){
        return new WeixinApi();
    }       
    
    @Bean
    public OAuthServiceDeractor getWeixinOAuthService(){
    	OAuthServiceDeractor oAuthServiceDeractor=  new WeixinOAuthService(new ServiceBuilder()
            .provider(weixinApi())
            .apiKey(env.getProperty("oAuth.weixin.appId"))
            .apiSecret(env.getProperty("oAuth.weixin.appKey"))
            .scope("snsapi_login")
            .callback(String.format(CALLBACK_URL, OAuthTypes.WEIXIN,""))
            .build());
    	
    	context.getServletContext().setAttribute("weixinAuthorizationUrl", oAuthServiceDeractor.getAuthorizationUrl());
    	return oAuthServiceDeractor;
    }
    
    @Bean
    public QQApi qqApi(){
        return new QQApi(env.getProperty("oAuth.qq.state"));
    }
    
    @Bean
    public OAuthServiceDeractor getQQOAuthService(){
    	OAuthServiceDeractor oAuthServiceDeractor= new QQOAuthService(new ServiceBuilder()
        .provider(qqApi())
        .apiKey(env.getProperty("oAuth.qq.appId"))
        .apiSecret(env.getProperty("oAuth.qq.appKey"))
        .callback(String.format(CALLBACK_URL, OAuthTypes.QQ,""))
        .build());

		context.getServletContext().setAttribute("qqAuthorizationUrl", oAuthServiceDeractor.getAuthorizationUrl());
		return oAuthServiceDeractor;
    	
    }
    
    @Bean
    public IOAuthUrlHelper githubOAuthUrlHelper(){
    	ServiceBuilder sb = new ServiceBuilder()
        .provider(githubApi())
        .apiKey(env.getProperty("oAuth.github.appId"))
        .apiSecret(env.getProperty("oAuth.github.appKey"));
    	
    	return new OAuthUrlHelper(sb, CALLBACK_URL, OAuthTypes.GITHUB);
    }
    
    @Bean
    public IOAuthUrlHelper weixinOAuthUrlHelper(){
    	ServiceBuilder sb = new ServiceBuilder()
        .provider(weixinApi())
        .apiKey(env.getProperty("oAuth.weixin.appId"))
        .apiSecret(env.getProperty("oAuth.weixin.appKey"))
        .scope("snsapi_login");
    	
    	return new OAuthUrlHelper(sb, CALLBACK_URL, OAuthTypes.WEIXIN);
    }
    
    @Bean
    public IOAuthUrlHelper qqOAuthUrlHelper(){
    	ServiceBuilder sb = new ServiceBuilder()
        .provider(qqApi())
        .apiKey(env.getProperty("oAuth.qq.appId"))
        .apiSecret(env.getProperty("oAuth.qq.appKey"));
    	
    	return new OAuthUrlHelper(sb, CALLBACK_URL, OAuthTypes.QQ);
    }
}
