package com.codeReading.busi.action.user;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeReading.core.framework.web.BaseAction;

/**
 * 用户注册、登录相关内容
 * 
 * @author Rofly
 */
@Controller
public class UserAuthAction extends BaseAction {
	private Logger log = LoggerFactory.getLogger(UserAuthAction.class);

	/**
	 * 请求登录页面
	 * 
	 * @param error
	 *            是否是登录出错访问
	 * @param logout
	 *            是否是注销后访问
	 * @return 注册页面名
	 */
	@RequestMapping("login")
	public String login(String error, String logout) {
		log.info("#访问登录页面, error={}, logout={}", error, logout);
		return "login";
	}

	/**
	 * logout处理
	 * 
	 * @return 登出处理
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, String preurl) {
		log.info("#用户登出");
		request.getSession().removeAttribute("nickname");
		request.getSession().removeAttribute("userid");
		
		return "redirect:" + preurl;
	}	
	
}
