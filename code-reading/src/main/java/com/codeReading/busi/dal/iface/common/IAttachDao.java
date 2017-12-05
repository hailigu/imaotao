package com.codeReading.busi.dal.iface.common;

import org.apache.ibatis.exceptions.PersistenceException;

import com.codeReading.busi.dal.model.Attach;
import com.codeReading.core.framework.db.BaseDao;

public interface IAttachDao extends BaseDao<Attach> {
	/**
	 * 获取附件信息
	 */
	public Attach get(String attachid) throws PersistenceException;
}
