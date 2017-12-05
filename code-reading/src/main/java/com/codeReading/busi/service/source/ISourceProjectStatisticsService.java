package com.codeReading.busi.service.source;

import com.codeReading.busi.dal.model.SourceProjectStatistics;
import com.codeReading.core.framework.web.InnerResultData;

public interface ISourceProjectStatisticsService {

	/**
	 * 添加注释数
	 * @param projectid
	 * @param count
	 * @return
	 * @throws Exception
	 */
	public InnerResultData<SourceProjectStatistics> addAnnotationCount(String projectid, int count) throws Exception;

	/**
	 * 
	 * @param projectid
	 * @param count
	 * @return
	 * @throws Exception
	 */
	public InnerResultData<SourceProjectStatistics> addWatchCount(String projectid, int count) throws Exception;

}
