package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
  * @commons: t_source_project 数据持久化对象
  * @vision: 1.0.1 
  * @comment:  源码表
  */
public class SourceProject implements Serializable{
	private static final long serialVersionUID = 1L;    
	public static final String PROJECTID="projectid";  //源码编号
	
	private String projectid;  //源码编号
	private String name;  //源码名称
	private String description;  //源码描述
	private String logo;  //源码的logo
	private String uploader;  //上传者
	private String sourcepath;  //源码工程跟目录
	private String datapath;  //
	private String projectpath;  //
	private String state;  //状态:正常（2）、关闭（3）、删除（4）、锁定（6）
	private Timestamp modtime;  //修改时间
	private Timestamp intime;  //入库时间

	
	public String getProjectid(){
		return this.projectid;
	}
	public void setProjectid(String projectid){
		this.projectid = projectid;
	}
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getDescription(){
		return this.description;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public String getLogo(){
		return this.logo;
	}
	public void setLogo(String logo){
		this.logo = logo;
	}
	public String getUploader(){
		return this.uploader;
	}
	public void setUploader(String uploader){
		this.uploader = uploader;
	}
	public String getSourcepath(){
		return this.sourcepath;
	}
	public void setSourcepath(String sourcepath){
		this.sourcepath = sourcepath;
	}
	public String getState(){
		return this.state;
	}
	public void setState(String state){
		this.state = state;
	}
	public Timestamp getModtime(){
		return this.modtime;
	}
	public void setModtime(Timestamp modtime){
		this.modtime = modtime;
	}
	public Timestamp getIntime(){
		return this.intime;
	}
	public void setIntime(Timestamp intime){
		this.intime = intime;
	}
	public String getDatapath(){
		return this.datapath;
	}
	public void setDatapath(String datapath){
		this.datapath = datapath;
	}
	public String getProjectpath(){
		return this.projectpath;
	}
	public void setProjectpath(String projectpath){
		this.projectpath = projectpath;
	}
}