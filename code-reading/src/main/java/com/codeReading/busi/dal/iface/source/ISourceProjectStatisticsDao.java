package com.codeReading.busi.dal.iface.source;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.codeReading.core.framework.db.BaseDao;

import org.apache.ibatis.exceptions.PersistenceException;

import com.codeReading.busi.dal.model.SourceProjectStatistics;
    
/**
  * @commons: ISourceProjectStatisticsDao 数据访问对象
  * @vision: 1.0.1
  */
@Repository
public interface ISourceProjectStatisticsDao extends BaseDao<SourceProjectStatistics>{
	public SourceProjectStatistics get(String projectid) throws PersistenceException;

	/**
	 * 增加注释数
	 * @param params projectid--不能为空；annotationcount--注释数
	 * @throws PersistenceException
	 */
	public void addAnnotationCount(Map<String, Object> params) throws PersistenceException;
	
	/**
	 * 增加关注
	 * @param params projectid--不能为空；watchcount--关注
	 * @throws PersistenceException
	 */
	public void addWatchCount(Map<String, Object> params) throws PersistenceException;
}