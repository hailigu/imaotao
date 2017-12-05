package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserService implements Serializable {
	private static final long serialVersionUID = 1L;

	private String userid;
	private Boolean isrealname;
	private Integer logincount;
	private Integer onlinetimecount;
	private Timestamp logintime;
	private Timestamp modtime;
	private Timestamp intime;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Boolean getIsrealname() {
		return isrealname;
	}
	public void setIsrealname(Boolean isrealname) {
		this.isrealname = isrealname;
	}
	public Integer getLogincount() {
		return logincount;
	}
	public void setLogincount(Integer logincount) {
		this.logincount = logincount;
	}
	public Integer getOnlinetimecount() {
		return onlinetimecount;
	}
	public void setOnlinetimecount(Integer onlinetimecount) {
		this.onlinetimecount = onlinetimecount;
	}
	public Timestamp getLogintime() {
		return logintime;
	}
	public void setLogintime(Timestamp logintime) {
		this.logintime = logintime;
	}
	public Timestamp getModtime() {
		return modtime;
	}
	public void setModtime(Timestamp modtime) {
		this.modtime = modtime;
	}
	public Timestamp getIntime() {
		return intime;
	}
	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}
}
