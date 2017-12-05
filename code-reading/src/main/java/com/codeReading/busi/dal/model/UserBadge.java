package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserBadge implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String userbadgeid;
	private String userid;
	private String badgeid;
	private String comment;
	private Timestamp modtime;
	private Timestamp intime;
	/**
	 * @return the userbadgeid
	 */
	public String getUserbadgeid() {
		return userbadgeid;
	}
	/**
	 * @param userbadgeid the userbadgeid to set
	 */
	public void setUserbadgeid(String userbadgeid) {
		this.userbadgeid = userbadgeid;
	}
	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * @return the badgeid
	 */
	public String getBadgeid() {
		return badgeid;
	}
	/**
	 * @param badgeid the badgeid to set
	 */
	public void setBadgeid(String badgeid) {
		this.badgeid = badgeid;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the modtime
	 */
	public Timestamp getModtime() {
		return modtime;
	}
	/**
	 * @param modtime the modtime to set
	 */
	public void setModtime(Timestamp modtime) {
		this.modtime = modtime;
	}
	/**
	 * @return the intime
	 */
	public Timestamp getIntime() {
		return intime;
	}
	/**
	 * @param intime the intime to set
	 */
	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}
	
	
}
