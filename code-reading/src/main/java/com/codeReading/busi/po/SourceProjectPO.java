package com.codeReading.busi.po;

import java.io.Serializable;
import java.sql.Timestamp;

public class SourceProjectPO implements Serializable {
	private static final long serialVersionUID = 1L;    
	public static final String PROJECTID="projectid";  //源码编号
	
	private String projectid;  //源码编号
	private String name;  //源码名称
	private String description;  //源码描述
	private String logo;  //源码的logo
	private String uploader;  //上传者
	private String sourcepath;  //源码工程跟目录
	private String state;  //状态:正常（2）、关闭（3）、删除（4）、锁定（6）
	private Timestamp modtime;  //修改时间
	private Timestamp intime;  //入库时间
	private String datapath;  //
	private String projectpath;  //
	/**
	 * @return the projectid
	 */
	public String getProjectid() {
		return projectid;
	}
	/**
	 * @param projectid the projectid to set
	 */
	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}
	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}
	/**
	 * @return the uploader
	 */
	public String getUploader() {
		return uploader;
	}
	/**
	 * @param uploader the uploader to set
	 */
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	/**
	 * @return the sourcepath
	 */
	public String getSourcepath() {
		return sourcepath;
	}
	/**
	 * @param sourcepath the sourcepath to set
	 */
	public void setSourcepath(String sourcepath) {
		this.sourcepath = sourcepath;
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
	/**
	 * @return the datapath
	 */
	public String getDatapath() {
		return datapath;
	}
	/**
	 * @param datapath the datapath to set
	 */
	public void setDatapath(String datapath) {
		this.datapath = datapath;
	}
	/**
	 * @return the projectpath
	 */
	public String getProjectpath() {
		return projectpath;
	}
	/**
	 * @param projectpath the projectpath to set
	 */
	public void setProjectpath(String projectpath) {
		this.projectpath = projectpath;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SourceProjectPO [projectid=" + projectid + ", name=" + name + ", description=" + description
				+ ", logo=" + logo + ", uploader=" + uploader + ", sourcepath=" + sourcepath + ", state=" + state
				+ ", modtime=" + modtime + ", intime=" + intime + ", datapath=" + datapath + ", projectpath="
				+ projectpath + "]";
	}
}
