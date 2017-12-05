package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
  * @commons: t_support 数据持久化对象
  * @vision: 1.0.1 
  * @comment:  点赞记录表
  */
public class Support implements Serializable{
	private static final long serialVersionUID = 1L;    
	public static final String SUPPORTID="supportid";  //点赞编号
	
	private String supportid;  //点赞编号
	private String userid;  //用户编号
	private String targetid;  //点赞目标
	private String targettype;  //点赞目标类型：1.文章 2.文章评论 3.注释
	private Integer orientation;  //赞还是踩：1.赞  -1.踩
	private Timestamp intime;  //入库时间
	
	public String getSupportid(){
		return this.supportid;
	}
	public void setSupportid(String supportid){
		this.supportid = supportid;
	}
	public String getUserid(){
		return this.userid;
	}
	public void setUserid(String userid){
		this.userid = userid;
	}
	public String getTargetid(){
		return this.targetid;
	}
	public void setTargetid(String targetid){
		this.targetid = targetid;
	}
	public String getTargettype(){
		return this.targettype;
	}
	public void setTargettype(String targettype){
		this.targettype = targettype;
	}
	public Integer getOrientation(){
		return this.orientation;
	}
	public void setOrientation(Integer orientation){
		this.orientation = orientation;
	}
	public Timestamp getIntime(){
		return this.intime;
	}
	public void setIntime(Timestamp intime){
		this.intime = intime;
	}
}