package com.codeReading.busi.po;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class UserPO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String keyinfo;
	private String password;
	private String nickname;
	private String verificationCode;
	
	public String getKeyinfo() {
		return keyinfo;
	}
	public void setKeyinfo(String keyinfo) {
		this.keyinfo = StringUtils.trim(keyinfo).replaceAll("<", "&lt;");
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = StringUtils.trim(nickname).replaceAll("<", "&lt;");
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = StringUtils.trim(verificationCode).replaceAll("<", "&lt;");
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if(null != keyinfo)sb.append("keyinfo").append("=").append(keyinfo).append(", ");
		if(null != nickname)sb.append("nickname").append("=").append(nickname).append(", ");
		if(null != password)sb.append("password").append("={").append(password.length()).append("}, ");
		if(null != verificationCode)sb.append("verificationCode").append("=").append(verificationCode).append(", ");
		sb.append("]");
		return sb.toString();
	}
}
