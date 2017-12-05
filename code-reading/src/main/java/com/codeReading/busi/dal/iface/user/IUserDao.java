package com.codeReading.busi.dal.iface.user;

import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;

import com.codeReading.busi.dal.model.User;
import com.codeReading.core.framework.db.BaseDao;

public interface IUserDao extends BaseDao<User> {
	/**
	 * 根据主键获取用户信息
	 * @param userid
	 * @return
	 * @throws PersistenceException
	 */
	public User get(String userid) throws PersistenceException;
	
	/**
	 * 根据用户核心登录信息获取用户核心信息
	 * @param keyinfo
	 * @return
	 * @throws PersistenceException
	 */
	public Map<String, Object> getUserCoreInfo(String keyinfo) throws PersistenceException;
	/**
	 * 根据用户核心登录信息获取用户绑定信息
	 * @param userid
	 * @return
	 * @throws PersistenceException
	 */
	public Map<String, Object> getUserBindInfo(String userid) throws PersistenceException;
	/**
	 * 根据用户核心登录信息获取用户信息
	 * @param keyinfo
	 * @return
	 * @throws PersistenceException
	 */
	public User getByUserKeyinfo(String keyinfo) throws PersistenceException;
	/**
	 * 判断用户昵称是否已经存在
	 * @param nickname 昵称（小写）
	 * @return 是否已经存在该昵称
	 * @throws PersistenceException
	 */
	public boolean existNickname(String nickname) throws PersistenceException;
}
