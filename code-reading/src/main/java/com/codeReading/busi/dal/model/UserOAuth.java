package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserOAuth implements Serializable {
	private static final long serialVersionUID = 1L;

	private String oid;
	private String userid;
	private String oauthtype;
	private String oauthid;
	private Timestamp modtime;
	private Timestamp intime;
	
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getOauthid() {
		return oauthid;
	}
	public void setOauthid(String oauthid) {
		this.oauthid = oauthid;
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
	public String getOauthtype() {
		return oauthtype;
	}
	public void setOauthtype(String oauthtype) {
		this.oauthtype = oauthtype;
	}
}
