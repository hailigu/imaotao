package com.codeReading.busi.action.info;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeReading.busi.po.TagPO;
import com.codeReading.busi.service.info.ITagService;
import com.codeReading.core.framework.web.BaseAction;
import com.codeReading.core.framework.web.ResultData;

/**
 * 标签相关内容
 * @author: liuxue
 */
@Controller
@RequestMapping("tag")
public class TagAction extends BaseAction {
	private Logger log = LoggerFactory.getLogger(TagAction.class);

	@Autowired
	private ITagService tagService;	
	
	@RequestMapping("/get")
	public void get(String tagid, Map<String, Map<String, Object>> result){
		log.info("#开始查询标签信息， tagid=[{}].", tagid);
		try {
			ResultData data = tagService.get(tagid);
			collect(data, result);
		} catch (Exception e) {
			collect(e, result);
		}
	}
	
	@RequestMapping(value="/find-input-tips", method=RequestMethod.POST)
	public void findInputTips(String tagname, Integer pageSize, Map<String, Map<String, Object>> result){
		try {
			log.info("#开始查询标签信息， tagname=[{}].", tagname);
			if(null == pageSize)pageSize = 5;
			ResultData data = tagService.filterByTagname(tagname, pageSize);
			collect(data, result);
		} catch (Exception e) {
			collect(e, result);
		}
	}
	
	@RequestMapping("/find-all")
	public void find(Map<String, Map<String, Object>> result){
		log.info("#开始查找所有标签信息");
		try {
			ResultData data = tagService.findAll();
			collect(data, result);
		} catch (Exception e) {
			collect(e, result);
		}
	}
	
	@RequestMapping(value="/add", method={RequestMethod.POST, RequestMethod.GET})
	public void add(TagPO tagpo, HttpServletRequest hsr, Map<String, Map<String, Object>> result){
		log.info("#开始查找添加标签信息， [{}]", tagpo);
		try {
			ResultData data = tagService.add(tagpo, hsr.getSession().getAttribute("userid").toString());
			collect(data, result);
		} catch (Exception e) {
			collect(e, result);
		}
	}
	
	@RequestMapping("/delete")
	public void delete(String tagid, Principal principal, Map<String, Map<String, Object>> result){
		log.info("#开始删除标签信息对象，tagid={}.", tagid);
		try {
			ResultData resData = tagService.delete(tagid, principal.getName());
			collect(resData, result);
		} catch (Exception e) {
			collect(e, result);
		}
	}
	
	@RequestMapping("/add-history-tag")
	public void addHistoryTag(String tagid, String fieldid, Principal principal, HttpServletRequest request, 
			Map<String, Map<String, Object>> result) throws Exception {
		log.info("#开始添加历史标签 tagid={}", tagid);
		try{
			ResultData resData = tagService.addHistoryTag(principal.getName(), tagid);
			collect(resData, result);
		}catch (Exception e) {
			collect(e, result);
		}
	}
}