package com.codeReading.busi.service.source.impl;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeReading.busi.dal.iface.source.ISourceProjectStatisticsDao;
import com.codeReading.busi.dal.model.SourceProjectStatistics;
import com.codeReading.busi.service.source.ISourceProjectStatisticsService;
import com.codeReading.core.framework.web.BaseService;
import com.codeReading.core.framework.web.InnerResultData;


@Service
public class SourceProjectStatisticsServiceImpl extends BaseService implements ISourceProjectStatisticsService{
	private Logger log = LoggerFactory.getLogger(SourceProjectStatisticsServiceImpl.class);
	
	@Autowired
	private ISourceProjectStatisticsDao sourceProjectStatisticsDao ;
	
	@Override
	public InnerResultData<SourceProjectStatistics> addAnnotationCount(String projectid, int count) throws Exception{
		log.info("###[服务] 开始 增加注释数 projectid={}, count={}", projectid, count);
		InnerResultData<SourceProjectStatistics> result = new InnerResultData<SourceProjectStatistics>();
		
		SourceProjectStatistics statistics = sourceProjectStatisticsDao.get(projectid);
		if(null == statistics){
			SourceProjectStatistics new_statistics = new SourceProjectStatistics();
			new_statistics.setProjectid(projectid);
			new_statistics.setAnnotationcount(count);
			new_statistics.setWatchcount(0);
			sourceProjectStatisticsDao.insert(new_statistics);
			result.setData(new_statistics);
		}else{
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("annotationcount", count);
			params.put("projectid", projectid);
			sourceProjectStatisticsDao.addAnnotationCount(params);

			statistics = sourceProjectStatisticsDao.get(projectid);
			result.setData(statistics);
		}
		
		log.info("###[服务] 完成 增加注释数 result={}", result);
		return result;
	}
	
	@Override
	public InnerResultData<SourceProjectStatistics> addWatchCount(String projectid, int count) throws Exception{
		log.info("###[服务] 开始 增加关注数 projectid={}, count={}", projectid, count);
		InnerResultData<SourceProjectStatistics> result = new InnerResultData<SourceProjectStatistics>();
		
		SourceProjectStatistics statistics = sourceProjectStatisticsDao.get(projectid);
		if(null == statistics){
			SourceProjectStatistics new_statistics = new SourceProjectStatistics();
			new_statistics.setProjectid(projectid);
			new_statistics.setAnnotationcount(0);
			new_statistics.setWatchcount(count);
			sourceProjectStatisticsDao.insert(new_statistics);
			result.setData(new_statistics);
		}else{
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("watchcount", count);
			params.put("projectid", projectid);
			sourceProjectStatisticsDao.addWatchCount(params);

			statistics = sourceProjectStatisticsDao.get(projectid);
			result.setData(statistics);
		}
		
		log.info("###[服务] 完成 增加关注数 result={}", result);
		return result;
	}
}
