package com.codeReading.busi.dal.iface.source;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.codeReading.core.framework.db.BaseDao;
import com.codeReading.core.framework.db.QueryBean;

import org.apache.ibatis.exceptions.PersistenceException;

import com.codeReading.busi.dal.model.SourceWatch;
    
/**
  * @commons: ISourceWatchDao 数据访问对象
  * @vision: 1.0.1
  */
@Repository
public interface ISourceWatchDao extends BaseDao<SourceWatch>{
	public SourceWatch get(String watchid) throws PersistenceException;
	
	/**
	 * 分页查找用户关注的源码信息
	 * @param query
	 * @return
	 * @throws PersistenceException
	 */
	public List<Map<String, Object>> findWatchByPage(QueryBean<Map<String, Object>> query) throws PersistenceException;
}