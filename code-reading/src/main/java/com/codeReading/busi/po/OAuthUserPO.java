package com.codeReading.busi.po;

import java.io.Serializable;

public class OAuthUserPO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String authid;
	private String authtype;
	private String nickname;
	private String avatar;
	
	public String getAuthid() {
		return authid;
	}
	public void setAuthid(String authid) {
		this.authid = authid;
	}
	public String getAuthtype() {
		return authtype;
	}
	public void setAuthtype(String authtype) {
		this.authtype = authtype;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OAuthUserPO [authid=" + authid + ", authtype=" + authtype + ", nickname=" + nickname + "]";
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	
}
