package com.codeReading.core.framework.security;

import org.springframework.security.authentication.BadCredentialsException;

import com.codeReading.core.framework.exception.UserLockedException;

public interface UserExtendChecker {
	public void check(ISecurityUser user) throws UserLockedException, BadCredentialsException;
}
