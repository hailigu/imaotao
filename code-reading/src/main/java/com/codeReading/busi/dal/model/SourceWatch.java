package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
  * @commons: t_source_watch 数据持久化对象
  * @vision: 1.0.1 
  * @comment:  源码工程关注表
  */
public class SourceWatch implements Serializable{
	private static final long serialVersionUID = 1L;    
	public static final String WATCHID="watchid";  //关注编号
	
	private String watchid;  //关注编号
	private String userid;  //关注者用户编号
	private String projectid;  //源码工程编号
	private String state;  //状态: 2.正常 4.删除
	private Timestamp modtime;  //修改时间
	private Timestamp intime;  //入库时间
	
	public String getWatchid(){
		return this.watchid;
	}
	public void setWatchid(String watchid){
		this.watchid = watchid;
	}
	public String getUserid(){
		return this.userid;
	}
	public void setUserid(String userid){
		this.userid = userid;
	}
	public String getProjectid(){
		return this.projectid;
	}
	public void setProjectid(String projectid){
		this.projectid = projectid;
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
}