package com.codeReading.busi.dal.iface.source;

import org.springframework.stereotype.Repository;
import com.codeReading.core.framework.db.BaseDao;
import org.apache.ibatis.exceptions.PersistenceException;
import com.codeReading.busi.dal.model.SourceFile;
    
/**
  * @commons: ISourceFileDao 数据访问对象
  * @vision: 1.0.1
  */
@Repository
public interface ISourceFileDao extends BaseDao<SourceFile>{
	public SourceFile get(String fileid) throws PersistenceException;
}