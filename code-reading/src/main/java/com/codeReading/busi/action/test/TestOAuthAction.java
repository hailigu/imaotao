package com.codeReading.busi.action.test;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeReading.busi.dpn.oauth.IOAuthUrlHelper;
import com.codeReading.core.framework.web.BaseAction;


@Controller
public class TestOAuthAction extends BaseAction {
private Logger log = LoggerFactory.getLogger(TestOAuthAction.class);
	
	@Autowired
	private IOAuthUrlHelper weixinOAuthUrlHelper;
	
	@RequestMapping(value="test/weixin", method=RequestMethod.GET)
	public String weixin(String projectid, String password, Map<String, Map<String, Object>> result) {
		try {
			log.info("#test/weixin");
			String url = weixinOAuthUrlHelper.getAuthorizationUrl("12345678");
			return "redirect:"+url;
		} catch (Exception e){
			collect(e, result);
			return "redirect:/";
		}
	}	
}
