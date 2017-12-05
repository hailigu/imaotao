package com.codeReading.busi.action.info;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeReading.core.framework.web.BaseAction;

@Controller
public class PageAction extends BaseAction {
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value={"/", "/index", "home"}, method=RequestMethod.GET)
	public String index(Map<String, Map<String, Object>> result){
		log.trace("访问系统首页");
		try {
			return "index";
		} catch (Exception e) {
			collect(e, result);
			return ERROR;
		}
	}

}
