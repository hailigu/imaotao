package com.codeReading.busi.dal.iface.user;

import org.apache.ibatis.exceptions.PersistenceException;

import com.codeReading.busi.dal.model.UserService;
import com.codeReading.core.framework.db.BaseDao;

public interface IUserServiceDao extends BaseDao<UserService>{
	/**
	 * 根据主键获取用户服务信息
	 * @param userid
	 * @return
	 * @throws PersistenceException
	 */
	public UserService get(String userid) throws PersistenceException;
	
	/**
	 * 处理登录时的业务，更改登录时间，更改登录次数
	 * @param userid
	 * @throws PersistenceException
	 */
	public void processLogin(String userid) throws PersistenceException;
}
