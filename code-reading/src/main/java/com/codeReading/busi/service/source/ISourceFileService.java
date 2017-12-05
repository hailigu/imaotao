package com.codeReading.busi.service.source;

import com.codeReading.busi.dal.model.SourceFile;
import com.codeReading.core.framework.web.InnerResultData;

public interface ISourceFileService {

	/**
	 * 添加源码文件到数据库
	 * @param sourceFile
	 * @return
	 * @throws Exception
	 */
	public InnerResultData<SourceFile> addFile(SourceFile sourceFile) throws Exception;

	/**
	 * 通过工程id和文件路径查找源码文件
	 * @param projectid
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public SourceFile getSourceFileByPath(String projectid, String path) throws Exception;

}
