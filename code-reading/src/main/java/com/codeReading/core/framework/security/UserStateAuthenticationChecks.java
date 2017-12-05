package com.codeReading.core.framework.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;

import com.codeReading.core.framework.exception.UserLockedException;

/**
 * 对用户状态信息的检查
 * @author Rofly
 */
public class UserStateAuthenticationChecks implements UserExtendChecker {
	private Logger log = LoggerFactory.getLogger(UserStateAuthenticationChecks.class);

	@Override
	public void check(ISecurityUser user) throws UserLockedException, BadCredentialsException {
		if(!user.isEnabled()){
			log.trace("User state check fail, userid={}, state={}", user.getUserid(), user.getState());
			if(user.isLocked()){
				throw new UserLockedException("用户已被锁定");
			}else{
				throw new BadCredentialsException("用户状态异常");
			}
		}
	}
}