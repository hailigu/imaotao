package com.codeReading.busi.action.source;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeReading.busi.action.info.TagAction;
import com.codeReading.busi.service.source.impl.SupportService;
import com.codeReading.core.framework.web.BaseAction;
import com.codeReading.core.framework.web.ResultData;

/**
 * 点赞记录 相关内容
 * @author Sun_k
 *
 */

@Controller
public class SupportAction extends BaseAction{
	private Logger log = LoggerFactory.getLogger(TagAction.class);
	
	@Autowired
	private SupportService supportService;
	
	@RequestMapping(value="support/get", method=RequestMethod.POST)
	public void getSupportData(HttpServletRequest  hsr, String targetid,Map<String, Map<String, Object>> result){
		try {
			String userid = hsr.getSession().getAttribute("userid").toString();
			
			log.info("#获取点赞记录信息， userid={} ,targetid={}", userid,targetid);
			ResultData data = supportService.getDetail(userid,targetid);
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}
}
