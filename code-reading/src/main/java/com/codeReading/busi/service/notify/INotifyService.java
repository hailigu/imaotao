package com.codeReading.busi.service.notify;

import com.codeReading.core.framework.db.PageBean;
import com.codeReading.core.framework.web.InnerResultData;
import com.codeReading.core.framework.web.ResultData;

public interface INotifyService {
	/**
	 * 给用户发送通知
	 * @param type 通知类型 @see NotifyConstant
	 * @param content 通知内容
	 * @param objectid 关联信息编号(orderid/accountid/userid/expertid)
	 * @param userids 待通知用户编号
	 * @return
	 * @throws Exception
	 */
	public InnerResultData<Boolean> notify(String type, String content, String objectid, String... userids) throws Exception;
	/**
	 * 查找用户相关的通知信息
	 * @param userid
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public ResultData findNotify(String userid, PageBean page) throws Exception;
	/**
	 * 查找用户相关的未读通知信息
	 * @param userid
	 * @param type 通知类型（可为null， 1.用户预约  2.账户变动 3.用户变动 4.信息审核 5.系统通告）
	 * @return
	 * @throws Exception
	 */
	public ResultData findUnreadNotify(String userid, String type) throws Exception;
	/**
	 * 统计用户新消息数据
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public ResultData countNewNotify(String userid) throws Exception;
	/**
	 * 将用户的系统通知设为已读
	 * @param userid 用户编号
	 * @param notifyid 通知编号（可为null）
	 * @param type 通知类型（可为null， 1.用户预约  2.账户变动 3.用户变动 4.信息审核 5.系统通告）
	 * @return
	 * @throws Exception
	 */
	public ResultData readNotifies(String userid, String notifyid, String type) throws Exception;
}
