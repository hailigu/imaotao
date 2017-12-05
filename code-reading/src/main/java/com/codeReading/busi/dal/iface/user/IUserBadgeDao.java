package com.codeReading.busi.dal.iface.user;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;

import com.codeReading.busi.dal.model.Badge;
import com.codeReading.busi.dal.model.UserBadge;
import com.codeReading.core.framework.db.BaseDao;

public interface IUserBadgeDao extends BaseDao<UserBadge>{
	
	/**
	 * 根据编号查询用户徽章
	 * @param userbadgeid 用户徽章编号
	 * @return
	 * @throws PersistenceException
	 */
	public UserBadge get(String userbadgeid)  throws PersistenceException;
	
	/**
	 * 查询指定用户的徽章
	 * @param userid 用户编号
	 * @return 用户的徽章
	 * @throws PersistenceException
	 */
	public List<Badge> findBadgesByUser(String userid)  throws PersistenceException;
}
