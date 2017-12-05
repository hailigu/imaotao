package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @table: t_user_contact
 * @commons: 用户联系人表
 */
public class UserContact implements Serializable {
	private static final long serialVersionUID = 1L;

	private String ucid; //联系方式编号
	private String userid; //用户编号
	private String contact; //联系方式
	private String type; //类型
	private boolean commonused; //是否常用
	private Timestamp modtime; //修改时间
	private Timestamp intime; //入库时间
	
	public String getUcid() {
		return ucid;
	}
	public void setUcid(String ucid) {
		this.ucid = ucid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean getCommonused() {
		return commonused;
	}
	public void setCommonused(boolean commonused) {
		this.commonused = commonused;
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
