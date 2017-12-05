package com.codeReading.busi.service.source.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeReading.busi.dal.iface.source.IAnnotationDao;
import com.codeReading.busi.dal.iface.source.ISourceProjectDao;
import com.codeReading.busi.dal.iface.source.ISourceProjectStatisticsDao;
import com.codeReading.busi.dal.model.SourceProject;
import com.codeReading.busi.dal.model.SourceProjectStatistics;
import com.codeReading.busi.po.ContributorPO;
import com.codeReading.busi.service.article.IArticleService;
import com.codeReading.busi.service.source.ISourceFileService;
import com.codeReading.busi.service.source.ISourceProjectPageService;
import com.codeReading.busi.service.source.ISourceProjectService;
import com.codeReading.busi.service.user.IUserService;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.framework.web.ResultData;


@Service
public class SourceProjectPageService extends BaseService implements ISourceProjectPageService {
	private Logger log = LoggerFactory.getLogger(SourceProjectPageService.class);
	
	@Autowired
	private ISourceProjectDao sourceProjectDao;
	@Autowired
	private ISourceProjectStatisticsDao sourceProjectStatisticsDao;
	@Autowired
	private ISourceProjectService sourceProjectService;
	@Autowired
	private IArticleService articleService;
	@Autowired
	private ISourceFileService sourceFileService;
	@Autowired
	private IUserService userService;
	@Autowired 
	private IAnnotationDao annotationDao;
	
	@Override
	public ResultData prepareProjectPageByName(String projectname) throws Exception{
		log.info("###[服务] 开始 准备工程首页 projectname={}", projectname);
		ResultData result = ResultData.init();
		
		//源码工程信息
		SourceProject sourceProject = sourceProjectService.getSourceProjectByName(projectname);
		result.setData("sourceproject", sourceProject);
		
		//源码工程统计信息
		SourceProjectStatistics sourceProjectStatistics = sourceProjectStatisticsDao.get(sourceProject.getProjectid());
		result.setData("statistics", sourceProjectStatistics);
		
		//源码工程对应的贡献者信息
		List<ContributorPO> contributors = sourceProjectDao.getProjectContributors(sourceProject.getProjectid());
		result.setData("contributors", contributors);	
				
		log.info("###[服务] 完成 准备工程首页 result={}", result);
		return result;
	}
}
