package com.codeReading.busi.action.user;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.codeReading.busi.dal.iface.user.IUserDao;
import com.codeReading.busi.dal.iface.user.IUserOAuthDao;
import com.codeReading.busi.dal.model.User;
import com.codeReading.busi.dal.model.UserOAuth;
import com.codeReading.busi.dpn.enums.UserState;
import com.codeReading.busi.dpn.oauth.IOAuthUrlHelper;
import com.codeReading.busi.po.OAuthUserPO;
import com.codeReading.busi.service.oauth.impl.OAuthServiceDeractor;
import com.codeReading.busi.service.oauth.impl.OAuthServices;
import com.codeReading.core.framework.web.BaseAction;
import com.codeReading.core.util.SeqUtil;

/**
 * 第三方登录相关内容
 * 
 * @author Rofly
 */
@Controller
public class UserOAuthAction extends BaseAction {
    
    public static final Logger logger = LoggerFactory.getLogger(UserOAuthAction.class);
    
    @Autowired 
    OAuthServices oAuthServices;
    
    @Autowired IUserOAuthDao userOAuthDao;

    @Autowired IUserDao userDao;
    
	@Autowired
	private IOAuthUrlHelper githubOAuthUrlHelper;
	
	@Autowired
	private IOAuthUrlHelper weixinOAuthUrlHelper;
	
	@Autowired
	private IOAuthUrlHelper qqOAuthUrlHelper;
   
    @RequestMapping(value = "/oauth/{type}/callback", method=RequestMethod.GET)
    public String callback(@RequestParam(value = "code", required = true) String code,
            @PathVariable(value = "type") String type, String preUrl,
            HttpServletRequest request, Model model){
    	logger.info("#oauth callback preUrl={}", preUrl);
    	
    	OAuthServiceDeractor oAuthService = oAuthServices.getOAuthService(type);
        Token accessToken = oAuthService.getAccessToken(null, new Verifier(code));
        OAuthUserPO oAuthInfo = oAuthService.getUserOAuth(accessToken);
        
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("oauthtype", oAuthInfo.getAuthtype());
		params.put("oauthid", oAuthInfo.getAuthid());
        
		String userid = "";
		String nickname = "";
        UserOAuth oAuthUser = userOAuthDao.getByOAuthTypeAndOAuthId(params);
        if(oAuthUser == null){
    		User user = new User();
    		user.setUserid(SeqUtil.produceUserid());
    		user.setNickname(oAuthInfo.getNickname());
    		user.setAvatar(oAuthInfo.getAvatar());
    		user.setState(UserState.NORMAL);
    		
        	userDao.insert(user);
        	
        	UserOAuth useroauth = new UserOAuth();
        	useroauth.setOid(SeqUtil.produceOauthid());
        	useroauth.setOauthtype(type);
        	useroauth.setOauthid(oAuthInfo.getAuthid());
        	useroauth.setUserid(user.getUserid());        	
        	userOAuthDao.insert(useroauth);
        	
        	userid = user.getUserid();
        	nickname = user.getNickname();
        }else{
        	userid = oAuthUser.getUserid();
        	
        	User user = userDao.get(userid);
        	if(user != null){
        		nickname = user.getNickname();
        	}
        }
        
        request.getSession().setAttribute("userid", userid);
        request.getSession().setAttribute("nickname", nickname);
        return "redirect:"+preUrl;  
    }   
    
	@RequestMapping(value="oauth/recordUrl", method=RequestMethod.GET)
	public String recordUrl(String type, String url, Map<String, Map<String, Object>> result) {
		try {
			String oauthUrl="";
			url = URLEncoder.encode(url, "UTF-8");
			if ("weixin".equals(type)) {
				logger.info("微信登录，记录登录前URL url={}",url);
				oauthUrl = weixinOAuthUrlHelper.getAuthorizationUrl(url);
			}else if("github".equals(type)){
				logger.info("github登录，记录登录前URL url={}",url);
				oauthUrl = githubOAuthUrlHelper.getAuthorizationUrl(url);
			}else if ("qq".equals(type)) {
				logger.info("QQ登录，记录登录前URL url={}",url);
				oauthUrl = qqOAuthUrlHelper.getAuthorizationUrl(url);
			}
			logger.info("跳转URL url={}",oauthUrl);
			return "redirect:"+oauthUrl;
		} catch (Exception e){
			collect(e, result);
			return "redirect:/";
		}
	}

}