package com.codeReading.busi.action.user;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codeReading.busi.service.user.IPasswordService;
import com.codeReading.core.framework.web.BaseAction;
import com.codeReading.core.framework.web.ResultData;

@Controller
@RequestMapping("password")
public class PasswordAction extends BaseAction {
	private Logger log = LoggerFactory.getLogger(PasswordAction.class);
	
	@Autowired private IPasswordService passwordService;
	
	@RequestMapping("/forget")
	public String forget(String keyinfo, Map<String, Map<String, Object>> result) {
		try{
			log.info("#访问忘记密码页面 keyinfo={}", keyinfo);
			ResultData data = passwordService.forget(keyinfo);
			collect(data, result);
			return "forget";
		} catch(Exception e){
			collect(e, result);
			return ERROR;
		}
	}
}
