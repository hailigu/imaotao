package com.codeReading.core.framework.security;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeReading.core.framework.exception.DataAccessException;
import com.codeReading.core.util.DaoUtil;

/**
 * 过渡期为了兼容原有密码体系
 * 
 * @author Rofly
 */
public class CompatiblePasswordUserDetailProvider extends UserDetailProvider {
	private Logger log = LoggerFactory.getLogger(CompatiblePasswordUserDetailProvider.class);

	private final String UPDATE_USER_PASSWORD = "com.kiford.busi.dal.iface.user.IUserDao.modifyPassword";
	private final String UDPATE_WECHAT_USER_PASSWORD = "com.kiford.busi.dal.iface.user.IWechatUserDao.modifyPassword";

	/**
	 * 存放新密码到数据，覆盖旧密码
	 * 
	 * @param encode
	 * @throws DataAccessException
	 */
	public void presistentUserPassword(String userid, String encodedPassword) {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("userid", userid);
			param.put("password", encodedPassword);

			DaoUtil.getCommonDao().update(UPDATE_USER_PASSWORD, param);
			DaoUtil.getCommonDao().update(UDPATE_WECHAT_USER_PASSWORD, param);
		} catch (DataAccessException e) {
			log.error("存放新密码到数据，覆盖旧密码，操作数据库异常", e);
		}
	}
}
