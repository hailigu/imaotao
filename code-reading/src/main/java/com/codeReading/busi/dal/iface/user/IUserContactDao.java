package com.codeReading.busi.dal.iface.user;

import org.apache.ibatis.exceptions.PersistenceException;

import com.codeReading.busi.dal.model.UserContact;
import com.codeReading.core.framework.db.BaseDao;

public interface IUserContactDao extends BaseDao<UserContact> {
	/**
	 * 根据主键获取用户联系方式
	 * @param ucid
	 * @return
	 * @throws PersistenceException
	 */
	public UserContact get(String ucid) throws PersistenceException;
	
	/**
	 * 根据用户编号获取用户联系方式
	 * @param userid
	 * @return
	 * @throws PersistenceException
	 */
	public UserContact getByUserid(String userid) throws PersistenceException;
	
	/**
	 * 根据主键删除用户联系方式
	 * @param ucid
	 * @return
	 * @throws PersistenceException
	 */
	public UserContact delete(String ucid) throws PersistenceException;
	
	/**
	 * 根据用户编号删除用户联系方式
	 * @param userid
	 * @return
	 * @throws PersistenceException
	 */
	public void deleteByUserid(String userid) throws PersistenceException;
}
