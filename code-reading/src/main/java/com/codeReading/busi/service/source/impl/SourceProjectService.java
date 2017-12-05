package com.codeReading.busi.service.source.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.helper.StringUtil;
import org.opensolaris.opengrok.configuration.RuntimeEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.codeReading.busi.dal.iface.article.IArticleDao;
import com.codeReading.busi.dal.iface.source.ISourceProjectDao;
import com.codeReading.busi.dal.iface.source.ISourceProjectStatisticsDao;
import com.codeReading.busi.dal.iface.user.IUserDao;
import com.codeReading.busi.dal.model.Article;
import com.codeReading.busi.dal.model.SourceFile;
import com.codeReading.busi.dal.model.SourceProject;
import com.codeReading.busi.dal.model.SourceProjectStatistics;
import com.codeReading.busi.dal.model.User;
import com.codeReading.busi.dpn.enums.DataState;
import com.codeReading.busi.dpn.enums.SourceFileType;
import com.codeReading.busi.dpn.exception.source.SourceProjectNotExistException;
import com.codeReading.busi.po.ContributorPO;
import com.codeReading.busi.service.source.ISourceFileService;
import com.codeReading.busi.service.source.ISourceProjectService;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.db.QueryBean;
import com.codeReading.core.framework.exception.ParameterNotAllowException;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.framework.web.ResultData;
import com.codeReading.core.opengrok.OpengrokBean;
import com.codeReading.core.util.DateUtil;

@Component
public class SourceProjectService extends BaseService implements ISourceProjectService {
	private Logger log = LoggerFactory.getLogger(SourceProjectService.class);
	
	@Autowired
	private ISourceProjectDao sourceProjectDao;
	@Autowired
	private ISourceProjectStatisticsDao sourceProjectStatisticsDao;
	@Autowired
	private IArticleDao articleDao;
	@Autowired
	private ISourceFileService sourceFileService;
	@Autowired
	private IUserDao userDao;
	@Autowired private OpengrokBean opengrokBean;

	
	@Override
	public ResultData getProjectList() throws Exception {
		log.info("###[服务] 开始 请求工程列表");
		ResultData result = ResultData.init();
		
		//源码工程信息
		List<SourceProject> sourceProjects = sourceProjectDao.findAll();
		List<Map<String, Object>> projects = getFieldValueList(sourceProjects);
		for(Map<String, Object> project : projects){
			SourceProjectStatistics statistics = sourceProjectStatisticsDao.get((String)project.get("projectid"));
			project.put("statistics", statistics);
		}
		result.setData("sourceprojects", projects);
		
		log.info("###[服务] 开始 请求工程列表result={}", result);		
		return result;
	}	
	
	@Override
	public ResultData getDetail(String projectid) throws Exception {
		ResultData result = ResultData.init();
		
		//源码工程信息
		SourceProject sourceProject = sourceProjectDao.get(projectid);
		result.setData("sourceproject", sourceProject);
				
		User uploader = userDao.get(sourceProject.getUploader());
		result.setData("uploader", uploader.getNickname());
		
		String uploadtimestr = DateUtil.show(sourceProject.getIntime().getTime());
		result.setData("uploadtime", uploadtimestr);
		
		//源码工程统计信息
		SourceProjectStatistics sourceProjectStatistics = sourceProjectStatisticsDao.get(projectid);
		result.setData("sourceprojectstatistics", sourceProjectStatistics);
		
		//源码工程对应的文章信息
		List<Article> articles = articleDao.getByProjectId(projectid);
		result.setData("articles", articles);
		
		//源码工程对应的贡献者信息
		List<ContributorPO> contributors = sourceProjectDao.getProjectContributors(projectid);
		result.setData("contributors", contributors);

		return result;
	}

	public SourceProject getSourceProject(String projectid) throws Exception{
		log.info("### [内部服务] 根据编号获取源码工程 projectid={}", projectid);
		SourceProject sourceProject = sourceProjectDao.get(projectid);
		if(!DataState.NORMAL.equals(sourceProject.getState())){
			return null;
		}
		log.info("### [内部服务] 根据编号获取源码工程 SourceProject={}", sourceProject);
		return sourceProject;
	}
	
	@Override
	public SourceProject getSourceProjectByName(String projectname) throws Exception{
		log.info("### [内部服务] 根据名称获取源码工程 projectname={}", projectname);
		if(StringUtil.isBlank(projectname)){
			throw new ParameterNotAllowException();
		}
		SourceProject project = new SourceProject();
		project.setName(projectname);
		List<SourceProject> sourceProjects = sourceProjectDao.find(project);
		if(sourceProjects.size()>0 && DataState.NORMAL.equals(sourceProjects.get(0).getState())){
			project = sourceProjects.get(0);
		}else{
			project = null;
		}
		
		log.info("### [内部服务] 结束 根据名称获取源码工程 。");
		return project;
	}
	
	public boolean updateSourceProject(SourceProject sourceProject) throws Exception{
		log.info("### [内部服务] 开始 更新源码工程 sourceProject={}", sourceProject);
		Assert.isNull(sourceProject);
		Assert.isNull(sourceProject.getProjectid());
		sourceProjectDao.update(sourceProject);
		log.info("### [内部服务] 结束 更新源码工程");
		return true;
	}
	
	/**
	 * @see ISourceProjectService.updataProjectFiles 
	 */
	@Override
	public ResultData updataProjectFiles(String projectid) throws Exception{
		log.info("###开始 更新源码工程文件 projectid={}", projectid);
		ResultData result = ResultData.init();
		SourceProject sourceProject = sourceProjectDao.get(projectid);
		RuntimeEnvironment rEnv = RuntimeEnvironment.getInstance();
		String fileRootPath = rEnv.getSourceRootPath();
		String projectPath = fileRootPath + sourceProject.getProjectpath();
		File rootDir = new File (projectPath);
		if(null == rootDir || !rootDir.isDirectory()){
			throw new SourceProjectNotExistException();
		}
		
		updateFile(projectid, projectPath, null,rootDir);
		
		//
		log.info("########### reset RuntimeEnvironment!!!");
        RuntimeEnvironment env = RuntimeEnvironment.getInstance();
        String config = opengrokBean.getConfigurationpath();
        if (config == null) {
        	log.error("CONFIGURATION section missing in web.xml");
        } else {
            try {
                env.readConfiguration(new File(config));
            } catch (IOException ex) {
            	log.error("OpenGrok Configuration error. Failed to read config file: ", ex);
            }
        } 
		log.info("### 结束 更新源码工程文件");
		return result;
	}
	
	private void updateFile(String projectid, String projectPath, String superfileid, File file) throws Exception{
		SourceFile sourceFile = new SourceFile();
		sourceFile.setFilename(file.getName());
		sourceFile.setOwner(projectid);
		sourceFile.setState(DataState.NORMAL);
		sourceFile.setSuperpath(superfileid);
		
		sourceFile.setViewcount(0);
		
		if(file.isDirectory()){
			//添加本身
			sourceFile.setPath(file.getAbsolutePath().substring(projectPath.length()));
			sourceFile.setType(SourceFileType.DIR.getType());
			SourceFile sf = sourceFileService.addFile(sourceFile).getData();
			
			File[] subfiles = file.listFiles();
			if(null != subfiles){
				for(File subfile : subfiles){
					updateFile(projectid, projectPath, sf.getFileid(), subfile);
				}
			}
		}else{
			//添加本身
			sourceFile.setPath(file.getAbsolutePath().substring(projectPath.length()));
			sourceFile.setType(SourceFileType.FILE.getType());
			sourceFileService.addFile(sourceFile);
		}
	}
	
	@Override
	public ResultData findUserSources(String userid) throws Exception {
		log.info("### 开始 获取用户的注释信息 userid={}", userid);
		ResultData result = ResultData.init();
		List<SourceProject> sources = sourceProjectDao.findByUserid(userid);
		result.setData("sources", sources);
		result.setData("userid", userid);
		log.info("### 完成 修改注释result={}", result);
		return result;		
	}	
	
	@Override
	public ResultData findUserSources(String userid, PageBean page) throws Exception {
		log.debug("[服务] 开始获取用户源码工程 userid={}, page={}", userid, page);
		ResultData result = ResultData.init();
		page.setOrderKey("intime");
		page.setAscend("desc");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		List<Map<String, Object>> sourceProjectList = sourceProjectDao.findUserSourcesJoinStatistics(new QueryBean<Map<String, Object>>(page,params));
		result.setData("sourceprojectlist",sourceProjectList);
		result.setData("page", page);
		log.info("[服务] 完成获取用户源码工程列表  result={}", result);
		return result;
	}
}
