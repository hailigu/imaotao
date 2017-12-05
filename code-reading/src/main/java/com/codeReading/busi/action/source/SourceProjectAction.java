package com.codeReading.busi.action.source;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeReading.busi.dpn.enums.PageSizeType;
import com.codeReading.busi.service.source.ISourceProjectPageService;
import com.codeReading.busi.service.source.ISourceProjectService;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.web.BaseAction;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.core.util.PageBeanUtil;

/**
 * 相关源码
 * @author Sun_k
 *
 */
@Controller
public class SourceProjectAction extends BaseAction{
	private Logger log = LoggerFactory.getLogger(SourceProjectAction.class);
	
	@Autowired
	private ISourceProjectService sourceProjectService;
	@Autowired
	private ISourceProjectPageService sourceProjectPageService;
	
	@RequestMapping(value="projects", method=RequestMethod.GET)
	public String getProjectList(Map<String, Map<String, Object>> result) {
		try {
			log.info("#访问源码列表页");
			ResultData data = sourceProjectService.getProjectList();
			collect(data, result);
			return "projectlist";
		} catch (Exception e){
			collect(e, result);
			return ERROR;
		}
	}	
	
	@RequestMapping(value="s/{id}", method=RequestMethod.GET)
	public String getSourceShow(@PathVariable String id, Map<String, Map<String, Object>> result) {
		try {
			log.info("#访问源码详情页， projectid={}", id);
			ResultData data = sourceProjectService.getDetail(id);
			collect(data, result);
			return "source/source-detail";
		} catch (Exception e){
			collect(e, result);
			return ERROR;
		}
	}
	
	@RequestMapping(value="project/{projectname:.+}", method=RequestMethod.GET)
	public String projectPage(@PathVariable String projectname, Map<String, Map<String, Object>> result) {
		try {
			log.info("#访问源码详情页， projectname={}", projectname);
			ResultData data = sourceProjectPageService.prepareProjectPageByName(projectname);
			collect(data, result);
			return "source/project";
		} catch (Exception e){
			collect(e, result);
			return ERROR;
		}
	}
	
	@RequestMapping(value="/s/{userid}/user-sources", method=RequestMethod.GET)
	public String findUserSources(@PathVariable String userid, HttpServletRequest request, Map<String, Map<String, Object>> result) throws Exception{
		log.info("#开始 获取用户的源码工程信息， userid={}", userid);
		try {
			ResultData data = sourceProjectService.findUserSources(userid);
			collect(data, result);
			return "user/user-sourceProjects";
		} catch (Exception e){
			collect(e, result);
			return ERROR;
		}
	}	
	
	@RequestMapping(value="source/find-theusersources", method=RequestMethod.GET)
	public void findTheUserArticles(String userid, PageBean page,Map<String, Map<String, Object>> result) {
		try {
			PageBeanUtil.initialize(page, PageSizeType.SOURCEPROJECT);
			ResultData data = sourceProjectService.findUserSources(userid, page);
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}
}
