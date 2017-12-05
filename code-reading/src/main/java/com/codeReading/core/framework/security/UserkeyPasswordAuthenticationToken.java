package com.codeReading.core.framework.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class UserkeyPasswordAuthenticationToken extends AbstractAuthenticationToken {
	
	private final Object principal;
	private final String nickname;
	private Object credentials;
	
	public UserkeyPasswordAuthenticationToken(Object principal, String nickname, Object credentials) {
		super(null);
		this.principal = principal;
		this.nickname = nickname;
		this.credentials = credentials;
		setAuthenticated(false);
	}
	
	public UserkeyPasswordAuthenticationToken(Object principal, String nickname, Object credentials,
			Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.nickname = nickname;
		this.credentials = credentials;
		super.setAuthenticated(true); // must use super, as we override
	}
	
	@Override
	public Object getCredentials() {
		return this.credentials;
	}
	public String getNickname() {
		return nickname;
	}
	@Override
	public Object getPrincipal() {
		return principal;
	}
	
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}

		super.setAuthenticated(false);
	}
	
	private static final long serialVersionUID = 1L;
}
