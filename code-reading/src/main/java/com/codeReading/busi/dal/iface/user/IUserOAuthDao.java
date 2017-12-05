package com.codeReading.busi.dal.iface.user;

import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;

import com.codeReading.busi.dal.model.UserOAuth;
import com.codeReading.core.framework.db.BaseDao;

public interface IUserOAuthDao extends BaseDao<UserOAuth>{
	/**
	 * 根据主键获取用户服务信息
	 * @param userid
	 * @return
	 * @throws PersistenceException
	 */
	public UserOAuth get(String userid) throws PersistenceException;
	
	/**
	 * 根据授权类型和授权ID获取信息
	 * @param authtype
	 * @param authid
	 * 	 * @throws PersistenceException
	 */
	public UserOAuth getByOAuthTypeAndOAuthId(Map<String, Object> params) throws PersistenceException;	
}
