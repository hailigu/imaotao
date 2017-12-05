package com.codeReading.busi.service.source;

import com.codeReading.core.framework.web.ResultData;

public interface ISourceProjectPageService {
	
	/**
	 * 组织工程首页
	 * @param projectname
	 * @return
	 * @throws Exception
	 */
	public ResultData prepareProjectPageByName(String projectname) throws Exception;	
}
