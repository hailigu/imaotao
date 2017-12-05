package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

/** 投诉与建议 */
public class Suggest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String suggestid;
	private String content;
	private String contact;
	private String userid;
	private String path;
	private String state;
	private Timestamp modtime;
	private Timestamp intime;
	
	public String getSuggestid() {
		return suggestid;
	}
	public void setSuggestid(String suggestid) {
		this.suggestid = suggestid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
