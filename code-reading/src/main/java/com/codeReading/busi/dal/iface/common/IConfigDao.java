package com.codeReading.busi.dal.iface.common;

import org.apache.ibatis.exceptions.PersistenceException;

import com.codeReading.busi.dal.model.Attach;
import com.codeReading.busi.dal.model.Config;
import com.codeReading.core.framework.db.BaseDao;

public interface IConfigDao extends BaseDao<Attach> {
	/**
	 * 根据主键获取配置值
	 * 
	 * @param key
	 *            主键
	 * @return 配置值
	 * @throws PersistenceException
	 */
	public String getValue(String key) throws PersistenceException;

	/**
	 * 根据主键获取配置
	 * 
	 * @param key
	 *            主键
	 * @return 配置值
	 * @throws PersistenceException
	 */
	public Config get(String key) throws PersistenceException;
}
