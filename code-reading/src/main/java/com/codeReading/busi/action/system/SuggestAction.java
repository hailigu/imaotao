package com.codeReading.busi.action.system;

import java.security.Principal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeReading.busi.po.SuggestPO;
import com.codeReading.busi.service.system.ISuggestService;
import com.codeReading.core.framework.web.BaseAction;
import com.codeReading.core.framework.web.ResultData;

@Controller
@RequestMapping("suggest")
public class SuggestAction extends BaseAction {
	private Logger log = LoggerFactory.getLogger(SuggestAction.class);
	
	@Autowired ISuggestService suggestService;
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public void save(SuggestPO suggest, Principal principal, Map<String, Map<String, Object>> result){
		try {
			log.info("#开始保存投诉与建议信息 suggest={}", suggest);
			String userid = null;
			if(null != principal)userid = principal.getName();
			ResultData data = suggestService.save(suggest, userid);
			collect(data, result);
		}catch (Exception e){
			collect(e, result);
		}
	}
}
