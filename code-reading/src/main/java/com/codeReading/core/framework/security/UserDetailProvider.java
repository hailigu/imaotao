package com.codeReading.core.framework.security;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

import com.codeReading.core.util.DaoUtil;

/**
 * 根据用户关键信息查询用户基本信息
 * 
 * @author Rofly
 */
public class UserDetailProvider implements IUserDetailProvider {
	private Logger log = LoggerFactory.getLogger(UserDetailProvider.class);

	private String loadUserSqlTemplate = "com.kiford.busi.dal.iface.user.IUserDao.getUserCoreInfo";

	@Override
	public ISecurityUser loadUserByKeyinfo(String keyinfo) throws Throwable {
		Assert.notNull(keyinfo, "The key info for load user is not null.");

		log.trace("load user by key information {}", keyinfo);
		Map<String, Object> _user = DaoUtil.getCommonDao().selectOne(loadUserSqlTemplate, keyinfo);
		if (null != _user) {
			// 此处所有登录用户统一为用户角色
			SecurityUser user = new SecurityUser(
					(String) _user.get("userid"), 
					(String) _user.get("nickname"), 
					(String) _user.get("password"), 
					(String) _user.get("phone"),
					(String) _user.get("email"), 
					(String) _user.get("state")
			);
			return user;
		} else {
			throw new UsernameNotFoundException("用户不存在");
		}
	}

	public void setLoadUserSqlTemplate(String loadUserSqlTemplate) {
		this.loadUserSqlTemplate = loadUserSqlTemplate;
	}

}
