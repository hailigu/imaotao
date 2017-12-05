package com.codeReading.busi.service.source;

import com.codeReading.busi.dal.model.SourceProject;
import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.web.ResultData;

public interface ISourceProjectService {

	/**
	 * 获取源码及相关信息
	 * @param projectid 源码编号
	 * @return 源码结果数据
	 * @throws Exception
	 */
	public ResultData getDetail(String projectid) throws Exception;

	/**
	 * 把源码工程中的文件更新到数据库中
	 * @param projectpo	需要更新的工程
	 * @return
	 * @throws Exception
	 */
	public ResultData updataProjectFiles(String projectid) throws Exception;

	/**
	 * 通过工程名获取源码工程。
	 * @param projectname
	 * @return
	 * @throws Exception
	 */
	public SourceProject getSourceProjectByName(String projectname) throws Exception;

	/**
	 * 通过工程名获取源码工程。
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public ResultData findUserSources(String userid) throws Exception;
	
	/**
	 * 通过用户id和page获取源码工程。
	 * @param userid ,bean
	 * @return
	 * @throws Exception
	 */
	public ResultData findUserSources(String userid,PageBean bean) throws Exception;
	
	/**
	 * 获取所有源码工程。
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public ResultData getProjectList() throws Exception;
	
}
