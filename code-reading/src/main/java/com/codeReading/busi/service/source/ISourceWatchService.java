package com.codeReading.busi.service.source;

import com.codeReading.busi.dal.model.SourceProjectStatistics;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.web.InnerResultData;
import com.codeReading.core.framework.web.ResultData;

public interface ISourceWatchService {

	/**
	 * 添加源码工程关注
	 * @param projectid
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public InnerResultData<SourceProjectStatistics> addWatch(String projectid, String userid) throws Exception;

	public ResultData add(String projectid, String userid) throws Exception;
	
	/**
	 * 查找用于关注的源码工程信息
	 * @param userid
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public ResultData findWatch(String userid,PageBean page) throws Exception;

}
