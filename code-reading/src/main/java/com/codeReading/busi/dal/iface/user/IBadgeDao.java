package com.codeReading.busi.dal.iface.user;

import org.apache.ibatis.exceptions.PersistenceException;

import com.codeReading.busi.dal.model.Badge;
import com.codeReading.core.framework.db.BaseDao;

public interface IBadgeDao extends BaseDao<Badge>{
	
	/**
	 * 根据编号查询徽章
	 * @param userbadgeid 徽章编号
	 * @return
	 * @throws PersistenceException
	 */
	public Badge get(String badgeid)  throws PersistenceException;
}