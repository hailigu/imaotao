package com.codeReading.busi.po;

import java.io.Serializable;

/**
 * @commons: 数据传输对象(用户联系人)
 * @vision: 1.0.1
 */
public class ContributorPO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String userid; //用户ID
	private String nickname; //用户昵称
	private String support; //获得赞的数量
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSupport() {
		return support;
	}
	public void setSupport(String support) {
		this.support = support;
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
		return "ContributorPO [userid=" + userid + ", nickname=" + nickname + ", support=" + support + "]";
	}
	
	
}
