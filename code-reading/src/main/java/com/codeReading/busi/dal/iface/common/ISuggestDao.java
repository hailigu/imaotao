package com.codeReading.busi.dal.iface.common;

import org.apache.ibatis.exceptions.PersistenceException;

import com.codeReading.busi.dal.model.Suggest;
import com.codeReading.core.framework.db.BaseDao;

public interface ISuggestDao extends BaseDao<Suggest> {
	public Suggest get(String suggestid) throws PersistenceException;
}
