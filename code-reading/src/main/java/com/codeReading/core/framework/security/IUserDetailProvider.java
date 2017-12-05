package com.codeReading.core.framework.security;

/**
 * 根据提供的用户核心信息获取用户核心信息
 * @author Rofly
 */
public interface IUserDetailProvider {
	ISecurityUser loadUserByKeyinfo(String keyinfo) throws Throwable;
}
