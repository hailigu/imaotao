package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 徽章模型
 * @author mute
 *
 */
public class Badge implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String badgeid ;		//徽章号
	private String badgename ;		//徽章名
	private String description ;	//徽章描述
	private String icon ;			//徽章图标
	private String state ;			//状态  1：有效  2：无效
	private Timestamp modtime;		//修改时间
	private Timestamp intime;		//入库时间
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
	 * @return the badgename
	 */
	public String getBadgename() {
		return badgename;
	}
	/**
	 * @param badgename the badgename to set
	 */
	public void setBadgename(String badgename) {
		this.badgename = badgename;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
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
