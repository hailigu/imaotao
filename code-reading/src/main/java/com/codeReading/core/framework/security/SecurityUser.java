package com.codeReading.core.framework.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.codeReading.core.framework.Constant;

/**
 * 用户核心信息
 * @author Rofly
 */
public class SecurityUser implements ISecurityUser, Serializable {
	private static final long serialVersionUID = 1L;
	
	private String password;
	private final String userid;
	private final String nickname;
	private final String phone;
	private final String email;
	private final String state;
	private final Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	
	public SecurityUser(String userid, String nickname, String password, String phone, String email, String state) {
		this.userid = userid;
		this.nickname = nickname;
		this.phone = phone;
		this.email = email;
		this.state = state;
		this.password = password;
		authorities.add(new SimpleGrantedAuthority("USER"));
	}
	
	public SecurityUser(String userid, String nickname, String password, String phone, String email, String state, Set<GrantedAuthority> authorities) {
		this.userid = userid;
		this.nickname = nickname;
		this.phone = phone;
		this.email = email;
		this.state = state;
		this.password = password;
		this.authorities.addAll(authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getUserid() {
		return userid;
	}
	public String getNickname() {
		return nickname;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getPhone() {
		return phone;
	}
	@Override
	public String getEmail() {
		return email;
	}
	@Override
	public String getState() {
		return state;
	}
	
	@Override
	public boolean isEnabled() {
		return Constant.USER_STATE_NORMAL.equals(this.state) ? true : false;
	}

	@Override
	public boolean isDeleted() {
		return Constant.USER_STATE_DELETE.equals(this.state) ? true : false;
	}
	
	@Override
	public boolean isLocked() {
		return Constant.USER_STATE_LOCKED.equals(this.state) ? true : false;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("SecurityUser={")
			.append("userid:").append(userid)
			.append(", nickname:").append(nickname)
			.append(", state:").append(state)
			.append(", phone:").append(phone)
			.append(", email:").append(email)
			.append("}");
		return sb.toString();
	}
}
