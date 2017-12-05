package com.codeReading.busi.service.user;

import com.codeReading.busi.dal.model.UserContact;
import com.codeReading.busi.po.UserContactPO;
import com.codeReading.core.framework.web.InnerResultData;
import com.codeReading.core.framework.web.ResultData;

/**
 * @commonts: 用户联系人接口
 * @author: liuxue
 */
public interface IUserContactService {
	/**
	 * 获取联系方式操作
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public ResultData get(String userid) throws Exception;
	
	/**
	 * 保存联系方式操作
	 * @param contactpo
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public ResultData save(UserContactPO contactpo, String userid) throws Exception;
	/**
	 * 删除用户联系方式操作
	 * @param contactid 联系方式编号
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public ResultData delete(String contactid, String userid) throws Exception;
	
	/**
	 * [内部调用] 根据用户编号获取联系方式
	 * @param userid 用户编号
	 * @return 用户的联系方式
	 * @throws Exception
	 */
	public InnerResultData<UserContact> getByUserid(String userid) throws Exception;
}
