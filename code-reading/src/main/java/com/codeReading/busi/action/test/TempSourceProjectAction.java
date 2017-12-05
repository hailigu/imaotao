package com.codeReading.busi.action.test;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeReading.busi.service.source.impl.SourceProjectService;
import com.codeReading.core.framework.web.BaseAction;
import com.codeReading.core.framework.web.ResultData;

/**
 * 相关源码
 * @author tangqian
 *
 */
@Controller
public class TempSourceProjectAction extends BaseAction{
	private Logger log = LoggerFactory.getLogger(TempSourceProjectAction.class);
	
	@Autowired
	private SourceProjectService sourceProjectService;
	
	@RequestMapping(value="test/update-source-files", method=RequestMethod.GET)
	public String getProjectList(String projectid, String password, Map<String, Map<String, Object>> result) {
		try {
			log.info("#刷新工程文件列表");
			if(!"kiford".equals(password)){
				collect(ResultData.init(), result);
			}
			ResultData data = sourceProjectService.updataProjectFiles(projectid);
			collect(data, result);
			return "redirect:/projects";
		} catch (Exception e){
			collect(e, result);
			return "redirect:/";
		}
	}	
}
