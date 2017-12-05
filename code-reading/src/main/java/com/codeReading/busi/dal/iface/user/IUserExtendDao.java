package com.codeReading.busi.dal.iface.user;

import org.apache.ibatis.exceptions.PersistenceException;

import com.codeReading.busi.dal.model.UserExtend;
import com.codeReading.core.framework.db.BaseDao;

public interface IUserExtendDao extends BaseDao<UserExtend> {
	public UserExtend get(String userid) throws PersistenceException;
}
