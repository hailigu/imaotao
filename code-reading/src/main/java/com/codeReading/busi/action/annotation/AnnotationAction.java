package com.codeReading.busi.action.annotation;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.codeReading.busi.dpn.enums.PageSizeType;
import com.codeReading.busi.po.AnnotationPO;
import com.codeReading.busi.po.AnnotationSearchPO;
import com.codeReading.busi.service.annotation.IAnnotationService;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.web.BaseAction;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.core.util.PageBeanUtil;

@Controller
public class AnnotationAction extends BaseAction {
	private Logger log = LoggerFactory.getLogger(AnnotationAction.class);
	
	@Autowired private IAnnotationService annotationService;
	
	@RequestMapping(value="annotation/add", method=RequestMethod.POST)
	public void add(AnnotationPO annotationpo, HttpServletRequest request, Map<String, Map<String, Object>> result) throws Exception{
		log.info("#开始写注释， userid={}, annotationpo={}",request.getSession().getAttribute("userid").toString(), annotationpo);
		try {
			String userid = (String)request.getSession().getAttribute("userid");
			ResultData data = annotationService.add(annotationpo, userid);
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}
	
	@RequestMapping(value="annotation/load-by-file", method=RequestMethod.POST)
	public void loadByFile(String path, Map<String, Map<String, Object>> result) throws Exception{
		log.info("#开始 加载文件注释， path={}", path);
		try {
			ResultData data = annotationService.loadByFilePath(path);
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}
	
	@RequestMapping(value="annotation/load-by-dir", method=RequestMethod.POST)
	public void loadByDir(String path, Map<String, Map<String, Object>> result) throws Exception{
		log.info("#开始 加载目录注释， path={}", path);
		try {
			ResultData data = annotationService.loadByDir(path);
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}
	
	@RequestMapping(value="annotation/add-support", method=RequestMethod.POST)
	public void addSupport(String annotationid, int orientation, HttpServletRequest request, Map<String, Map<String, Object>> result) throws Exception{
		log.info("#开始 注释赞踩， annotationid={}, orientation={}", annotationid, orientation);
		String userid = (String)request.getSession().getAttribute("userid");
		try {
			ResultData data = annotationService.addSupport(userid, annotationid, orientation);
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}
	
	@RequestMapping(value="annotation/update", method=RequestMethod.POST)
	public void update(AnnotationPO annotationpo, HttpServletRequest request, Map<String, Map<String, Object>> result) throws Exception{
		String userid = (String)request.getSession().getAttribute("userid");
		log.info("#开始 修改注释， annotationpo={}, userid={}", annotationpo, userid);
		try {
			ResultData data = annotationService.update(annotationpo, userid);
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}

	@RequestMapping(value="annotation/find-theuserannotations", method=RequestMethod.POST)
	public void findTheUserArticles(String userid, PageBean page,Map<String, Map<String, Object>> result) {
		try {
			PageBeanUtil.initialize(page, PageSizeType.ANNOTATION);
			ResultData data = annotationService.findUserAnnotations(userid, page);
			collect(data, result);
		} catch (Exception e){
			collect(e, result);
		}
	}

	/**
	 * 查找注释
	 */
	@RequestMapping(value="annotation/find-annotation",method=RequestMethod.GET)
	public void findAnnotation(AnnotationSearchPO annotationSearchpo,PageBean page,Map<String, Map<String, Object>> result){
		try {
			PageBeanUtil.initialize(page, PageSizeType.ANNOTATION);
			ResultData data = annotationService.findAnnotations(annotationSearchpo, page);
			collect(data, result);
		} catch(Exception e) {
			collect(e, result);
		}
	}	
	
	/**
	 * 查找注释
	 */
	@RequestMapping(value="annotation/download-mine",method=RequestMethod.GET)
	public void downloadMine(HttpServletRequest request, HttpServletResponse response, Map<String, Map<String, Object>> result){
		try {
			String userid = (String)request.getSession().getAttribute("userid");
			if(null == userid) return;
			String annotations = annotationService.downloadMine(userid);
			String fileName = "annotations.html";
			response.reset();  
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");  
			response.addHeader("Content-Length", "" + annotations.getBytes().length);  
			response.setContentType("application/octet-stream;charset=UTF-8");  
			OutputStream outputStream = response.getOutputStream();
			OutputStreamWriter write = new OutputStreamWriter(outputStream,"UTF-8");
			BufferedWriter writer=new BufferedWriter(write);   
			writer.write(annotations);
			writer.close();
		} catch(Exception e) {
			collect(e, result);
		} 
	}	
}
