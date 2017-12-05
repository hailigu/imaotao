package com.codeReading.busi.service.annotation.impl;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeReading.busi.dal.iface.source.IAnnotationDao;
import com.codeReading.busi.dal.iface.user.IUserDao;
import com.codeReading.busi.dal.model.Annotation;
import com.codeReading.busi.dal.model.SourceFile;
import com.codeReading.busi.dal.model.SourceProject;
import com.codeReading.busi.dal.model.User;
import com.codeReading.busi.dal.model.UserAnnotation;
import com.codeReading.busi.dpn.enums.DataState;
import com.codeReading.busi.dpn.enums.SourceFileType;
import com.codeReading.busi.dpn.enums.SupportTargetType;
import com.codeReading.busi.dpn.exception.source.AnnotationHasExistException;
import com.codeReading.busi.dpn.exception.source.SourceFileNotExistException;
import com.codeReading.busi.po.AnnotationPO;
import com.codeReading.busi.po.AnnotationSearchPO;
import com.codeReading.busi.service.annotation.IAnnotationService;
import com.codeReading.busi.service.source.ISourceFileService;
import com.codeReading.busi.service.source.ISourceProjectService;
import com.codeReading.busi.service.source.ISourceProjectStatisticsService;
import com.codeReading.busi.service.source.ISourceWatchService;
import com.codeReading.busi.service.source.ISupportService;
import com.codeReading.busi.service.user.IUserService;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.db.QueryBean;
import com.codeReading.core.framework.exception.ParameterNotAllowException;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.core.util.DateUtil;
import com.codeReading.core.util.SeqUtil;
import com.github.rjeschke.txtmark.Processor;

@Service
public class AnnotationServiceImpl extends BaseService implements IAnnotationService {
	private Logger log = LoggerFactory.getLogger(AnnotationServiceImpl.class);
	
	@Autowired private IAnnotationDao annotationDao;
	@Autowired private IUserDao userDao;
	@Autowired private ISourceProjectService sourceProjectService;
	@Autowired private ISourceWatchService sourceWatchService;
	@Autowired private ISourceFileService sourceFiletService;
	@Autowired private IUserService userService;
	@Autowired private ISupportService supportService;
	@Autowired private ISourceProjectStatisticsService sourceProjectStatistics;
	
	@Override
	public ResultData add(AnnotationPO annotationpo, String userid) throws Exception{
		log.info("### 开始添加注释 annotationpo={}, userid={}", annotationpo, userid);
		ResultData result = ResultData.init();
		
		Map<String, String> projectAndFile  = separatePath(annotationpo.getPath());
		SourceProject sourceProject = sourceProjectService.getSourceProjectByName(projectAndFile.get("projectname"));
		if(null==sourceProject ){
			//请求的工程不存在
			throw new SourceFileNotExistException();
		}
		String filepath = projectAndFile.get("filepath");
		SourceFile sourceFile = sourceFiletService.getSourceFileByPath(sourceProject.getProjectid(), filepath);
		if(null==sourceFile){
			//请求的文件不存在
			throw new SourceFileNotExistException();
		}
		Annotation annotation = new Annotation();
		annotation.setUserid(userid);
		annotation.setFileid(sourceFile.getFileid());
		annotation.setLinenum(annotationpo.getLinenum());
		List<Annotation> exists = annotationDao.find(annotation);
		if(exists.size()>0){
			throw new AnnotationHasExistException();
		}
		annotation.setAnnotationid(SeqUtil.produceAnnotationid());
		annotation.setContent(annotationpo.getContent());
		annotation.setState(DataState.NORMAL);
		annotationDao.insert(annotation);
		//添加注释数
		sourceProjectStatistics.addAnnotationCount(sourceProject.getProjectid(), 1);
		//关注源码工程 
		sourceWatchService.addWatch(sourceProject.getProjectid(), userid);
		
		//组织数据
		annotation = annotationDao.get(annotation.getAnnotationid());
		Map<String, Object> anntationMap = getFieldValueMap(annotation);
		User user = userService.getUser(userid).getData();
		anntationMap.put("nickname", user.getNickname());
		anntationMap.put("avatar", user.getAvatar());
		result.setData("annotation", anntationMap);
		
		log.info("### 完成 添加注释result={}", result);
		return result;
	}
	
	private Map<String, String> separatePath(String path) throws Exception{
		//String servletPath = path;
		HashMap<String, String> result = new HashMap<String, String>();
		if (path == null || path.charAt(0) != '/' || path.length()<2) {
			return result;
		}
		//去掉第一个前缀 如'/xref' tq
		int idx = path.indexOf('/', 1);
		String resourcepath = (idx == -1) ? "/" : path.substring(idx, path.length());
		if ("/".equals(resourcepath)) {
			return result;
		}
		idx = resourcepath.indexOf('/', 1);
		if ("/".equals(resourcepath)) {
			return result;
		}
		String filepath = null;
		if(resourcepath.charAt(resourcepath.length()-1) == '/'){
			filepath = resourcepath.substring(idx, resourcepath.length()-1).replace("/", File.separator);
		}else{
			if(idx<0){
				filepath = "";
			}else{
				filepath = resourcepath.substring(idx, resourcepath.length()).replace("/", File.separator);
			}
		}
		result.put("projectname", resourcepath.substring(1, idx));
		result.put("filepath", filepath);
		return result;
	}
	
	
	@Override
	public ResultData loadByFilePath(String path) throws Exception{
		log.info("### 开始 根据文件加载注释 path={}", path);
		ResultData result = ResultData.init();
		
		Map<String, String> projectAndFile  = separatePath(path);
		SourceProject sourceProject = sourceProjectService.getSourceProjectByName(projectAndFile.get("projectname"));
		if(null==sourceProject ){
			//请求的工程不存在
			throw new SourceFileNotExistException();
		}
		String filepath = projectAndFile.get("filepath");
		SourceFile sourceFile = sourceFiletService.getSourceFileByPath(sourceProject.getProjectid(), filepath);
		if(null==sourceFile){
			//请求的文件不存在
			throw new SourceFileNotExistException();
		}

		List<Map<String, Object>> annotations = annotationDao.findByFileWithUserInfo(sourceFile.getFileid());
		
		result.setData("annotations", annotations);
		log.info("### 完成 根据文件加载注释 result={}", result);
		return result;
	}
	
	/**
	 * @see IAnnotationService.addSupport
	 */
    @Override
    public ResultData addSupport(String userid, String annotationid, int orientation) throws Exception{
    	log.info("### 开始 注释点赞userid={}, annotationid={}, orientation={}",userid, annotationid, orientation);
		ResultData result = ResultData.init();
		Annotation annotation = annotationDao.get(annotationid);
		if(null == annotation){
			throw new ParameterNotAllowException();
		}
		if(null == userid){
			throw new ParameterNotAllowException();
		}
		if(orientation != 1 && orientation != -1){
			throw new ParameterNotAllowException();
		}
		
		int supportResult = supportService.addSupport(userid, annotationid, SupportTargetType.ANNOTATION, orientation).getData();
		
		//更新注释中的点赞数		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("annotationid", annotation.getAnnotationid());
		params.put("supports", supportResult);
		annotationDao.addSupport(params);
		
		annotation = annotationDao.get(annotationid);
		
		Map<String, Object> anntationMap = getFieldValueMap(annotation);
		User user = userService.getUser(annotation.getUserid()).getData();
		anntationMap.put("nickname", user.getNickname());
		anntationMap.put("avatar", user.getAvatar());
		result.setData("annotation", anntationMap);
		
		log.info("### 开始 注释点赞result={}", result);
		return result;
    }
    
    @Override
    public ResultData update(AnnotationPO annotationpo, String userid) throws Exception{
		log.info("### 开始 修改注释 annotationpo={}, userid={}", annotationpo, userid);
		ResultData result = ResultData.init();
		
		Annotation annotation = annotationDao.get(annotationpo.getAnnotationid());
		if(null==annotation || !annotation.getUserid().equals(userid)){
			throw new ParameterNotAllowException();
		}
		
		Annotation annotationupdate = new Annotation();
		annotationupdate.setAnnotationid(annotationpo.getAnnotationid());
		annotationupdate.setContent(annotationpo.getContent());
		annotationupdate.setModtime(DateUtil.currentTimestamp());
		annotationDao.update(annotationupdate);
		
		//组织数据
		annotation = annotationDao.get(annotation.getAnnotationid());
		Map<String, Object> anntationMap = getFieldValueMap(annotation);
		User user = userService.getUser(userid).getData();
		anntationMap.put("nickname", user.getNickname());
		anntationMap.put("avatar", user.getAvatar());
		result.setData("annotation", anntationMap);
		
		log.info("### 完成 修改注释result={}", result);
		return result;
	}


	@Override
	public ResultData findUserAnnotations(String userid, PageBean page)
			throws Exception {
		log.info("### 开始 获取用户的注释信息 userid={}", userid);
		ResultData result = ResultData.init();
		page.setOrderKey("modtime");
		page.setAscend("desc");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		params.put("state", DataState.NORMAL);
		List<Map<String, Object>> annotationList = annotationDao.findDetailByPage(new QueryBean<Map<String, Object>>(page,params));
		result.setData("annotationlist",annotationList);
		result.setData("page", page);
		log.info("### 完成  获取用户的注释信息result={}", result);
		return result;	
	}
	
	@Override
	public ResultData loadByDir(String path) throws Exception{
		log.info("### 开始 根据mu加载注释 path={}", path);
		ResultData result = ResultData.init();
		
		Map<String, String> projectAndFile  = separatePath(path);
		SourceProject sourceProject = sourceProjectService.getSourceProjectByName(projectAndFile.get("projectname"));
		if(null==sourceProject ){
			//请求的工程不存在
			throw new SourceFileNotExistException();
		}
		String filepath = projectAndFile.get("filepath");
		SourceFile sourceFile = sourceFiletService.getSourceFileByPath(sourceProject.getProjectid(), filepath);
		if(null==sourceFile){
			//请求的文件不存在
			throw new SourceFileNotExistException();
		}
		if(!sourceFile.getType().equals(SourceFileType.DIR.getType())){
			throw new ParameterNotAllowException();
		}
		
		List<Map<String, Object>> annotations = annotationDao.findByDirWithUserInfo(sourceFile.getFileid());
		
		result.setData("annotations", annotations);
		log.info("### 完成 根据文件加载注释 result={}", result);
		return result;
	}
	
	
	@Override
	public ResultData findAnnotations(AnnotationSearchPO annotationSearch,
			PageBean page) throws Exception {
		log.debug("[服务] 开始搜索注释信息 annotation={}, page={}", annotationSearch, page);
		
		ResultData result = ResultData.init();
		page.setOrderKey("intime");
		page.setAscend("desc");
		List<Map<String, Object>> annotationList =null;

		annotationList = new LinkedList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("projectid", annotationSearch.getProjectid());
		annotationList = annotationDao.findDetailByPage(new QueryBean<Map<String, Object>>(page,params));
		
		List<Map<String, Object>> annotationSearchList = new LinkedList<Map<String, Object>>();
		//组织数据
		for(int j=0; j<annotationList.size(); j++){
			//文章信息
			Map<String, Object> annotationtemp = annotationList.get(j);			

			//用户信息
			User user = userDao.get(annotationtemp.get("userid").toString());
			annotationtemp.put("user", user);
			
			annotationSearchList.add(annotationtemp);
		}
		
		log.debug("通过搜索引擎获取搜索注释信息，结果{}条，page={}", annotationList.size(), page);
		result.setData("annotations", annotationSearchList);
		result.setData("page", page);
		
		log.info("[服务] 完成搜索注释信息  result={}", result);
		return result;
	}
		

	@Override
	public String downloadMine(String userid) throws Exception{
		log.info("### 开始 导出我的注释 userid={}", userid);
		String result = "<!DOCTYPE html>"+
						"<html lang=\"en\">"+
						"<head>"+
							"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"+
							"<meta charset=\"utf-8\">"+
							"<title>注释导出</title>"+
						"</head>"+
						"<body>";
		Annotation annotation = new Annotation();
		annotation.setUserid(userid);
		List<UserAnnotation> annotations = annotationDao.findByUserid(userid);
		if(annotations.isEmpty()){
			result += "<h2>您还没有添加任何注释</h2>";
		}else{
			for(UserAnnotation item : annotations){
				if(item.getLinenum()>0){
					result += "<h2><a href=\"http://www.imaotao.cn/xref/"+
							item.getProjectname()+item.getPath()+"#"+item.getLinenum()+"\">" +
							item.getProjectname()+item.getPath()+" 第"+item.getLinenum()+"行</a></h2>";
				}else{
					result += "<h2><a href=\"http://www.imaotao.cn/xref/"+
							item.getProjectname()+item.getPath()+"\">" +
							item.getProjectname()+item.getPath()+"</a></h2>";
				}
				
				result +=Processor.process(item.getContent());
			}
		}
		result+="</body></html>";
		log.info("### 完成 导出我的注释 ");
		return result;
	}
}
