package com.codeReading.busi.dal.iface.source;

import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.stereotype.Repository;

import com.codeReading.busi.dal.model.Support;
import com.codeReading.core.framework.db.BaseDao;
    
/**
  * @commons: ISupportDao 数据访问对象
  * @vision: 1.0.1
  */
@Repository
public interface ISupportDao extends BaseDao<Support>{
	public Support get(String supportid) throws PersistenceException;
	
	/**
	 * 
	 * @param params 两个参数为userid和targetid，都不能为空
	 * @return
	 * @throws PersistenceException
	 */
	public Support getByUserAndTarget(Map<String, Object> params) throws PersistenceException;
}