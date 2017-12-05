package com.codeReading.busi.dal.iface.source;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Repository;

import com.codeReading.busi.dal.model.SourceProject;
import com.codeReading.busi.po.ContributorPO;
import com.codeReading.core.framework.db.BaseDao;
import com.codeReading.core.framework.db.QueryBean;
    
/**
  * @commons: ISourceProjectDao 数据访问对象
  * @vision: 1.0.1
  */
@Repository
public interface ISourceProjectDao extends BaseDao<SourceProject>{
	public SourceProject get(String projectid) throws PersistenceException;
	public List<ContributorPO> getProjectContributors(String projectid) throws PersistenceException;
	public List<SourceProject> findByUserid(String userid) throws PersistenceException;
	public List<SourceProject> findUserSourcesByPage(QueryBean<Map<String, Object>> query) throws PersistenceException;
	public List<SourceProject> findAll() throws PersistenceException;
	public List<Map<String,Object>> findUserSourcesJoinStatistics(QueryBean<Map<String, Object>> query) throws PersistenceException;
}
