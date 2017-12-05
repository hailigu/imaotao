package com.codeReading.core.framework.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public interface ISecurityUser {
	Collection<? extends GrantedAuthority> getAuthorities();
	String getUserid();
	String getNickname();
	String getPassword();
	String getPhone();
	String getEmail();
	String getState();
	/**
	 * 用户是否可用
	 * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
	 */
	boolean isEnabled();
	/**
	 * 用户已删除
	 * @return <code>true</code> if the user is delete, <code>false</code> otherwise
	 */
	boolean isDeleted();
	/**
	 * 用户是否被管理员锁定
	 * @return <code>true</code> if the user is locked, <code>false</code> otherwise
	 */
	boolean isLocked();
}
