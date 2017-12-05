package com.codeReading.busi.service.source;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codeReading.busi.po.SourceSearchPO;
import com.codeReading.core.framework.web.ResultData;

public interface ISourceReadingService {

	/**
	 * 打开某个源码文件或目录
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ResultData reading(HttpServletRequest request,HttpServletResponse response) throws Exception;

	/**
	 * 源码内搜索
	 * @param sourceSearchPO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ResultData search(SourceSearchPO sourceSearchPO, HttpServletRequest request) throws Exception;

	public ResultData more(SourceSearchPO po, HttpServletRequest request) throws Exception;
}
