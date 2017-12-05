package com.codeReading.busi.dal.iface.notify;

import org.apache.ibatis.exceptions.PersistenceException;

import com.codeReading.busi.dal.model.Notify;
import com.codeReading.core.framework.db.BaseDao;

public interface INotifyDao extends BaseDao<Notify> {
	public Notify get(String notifyid) throws PersistenceException;
	/**
	 * 阅读相关通知
	 * @param notify 通知信息，只处理userid和notifyid
	 * @throws PersistenceException
	 */
	public void readNotifies(Notify notify) throws PersistenceException;
}
