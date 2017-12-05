package com.codeReading.busi.action.source;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeReading.busi.dpn.exception.source.SourceFileNotExistException;
import com.codeReading.busi.po.SourceSearchPO;
import com.codeReading.busi.service.source.ISourceReadingService;
import com.codeReading.core.framework.web.BaseAction;
import com.codeReading.core.framework.web.ResultData;


@Controller
public class SourceReadingAction extends BaseAction {
	private Logger log = LoggerFactory.getLogger(SourceReadingAction.class);
	
	@Autowired private ISourceReadingService sourceReadingService;
	
	@RequestMapping(value="xref/**", method=RequestMethod.GET)
	public String xref(HttpServletRequest request, HttpServletResponse response,
			Map<String, Map<String, Object>> result) throws Exception{
		log.info("#访问阅读源码页， getServletPath={}", request.getServletPath());
		try {
			ResultData data = sourceReadingService.reading(request, response);
			if("redirect".equals(data.getRetCode())){
				return "redirect:"+data.getReason();
			}
			collect(data, result);
			return "source/list";
		}catch(SourceFileNotExistException e){
			collect(e, result);
			return "error/404";
		}catch (Exception e){
			collect(e, result);
			return ERROR;
		}
	}
	
	@RequestMapping(value={"source/s", "source/search"}, method=RequestMethod.GET)
	public String search(SourceSearchPO sourceSearchPO, HttpServletRequest request, 
			Map<String, Map<String, Object>> result) throws Exception{
		log.info("#访问阅读源码页， sourceSearchPO={}", sourceSearchPO);
		try {
			ResultData data = sourceReadingService.search(sourceSearchPO, request);
			collect(data, result);
			return "source/search";
		} catch (Exception e){
			collect(e, result);
			return ERROR;
		}
	}
	
	@RequestMapping(value="more/**", method=RequestMethod.GET)
	public String more(SourceSearchPO sourceSearchPO, HttpServletRequest request, 
			Map<String, Map<String, Object>> result) throws Exception{
		log.info("#访问阅读源码页， sourceSearchPO={}", sourceSearchPO);
		try {
			ResultData data = sourceReadingService.more(sourceSearchPO, request);
			collect(data, result);
			return "source/more";
		} catch (Exception e){
			collect(e, result);
			return ERROR;
		}
	}
}
