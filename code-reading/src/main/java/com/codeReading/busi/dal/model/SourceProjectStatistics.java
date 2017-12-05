package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
  * @commons: t_source_project_statistics 数据持久化对象
  * @vision: 1.0.1 
  * @comment:  源码工程统计信息表
  */
public class SourceProjectStatistics implements Serializable{
	private static final long serialVersionUID = 1L;    
	public static final String PROJECTID="projectid";  //源码编号
	
	private String projectid;  //源码编号
	private Integer annotationcount;  //源码注释数
	private Integer watchcount;  //源码关注数
	private Timestamp modtime;  //修改时间
	private Timestamp intime;  //入库时间
	
	public String getProjectid(){
		return this.projectid;
	}
	public void setProjectid(String projectid){
		this.projectid = projectid;
	}
	public Integer getAnnotationcount(){
		return this.annotationcount;
	}
	public void setAnnotationcount(Integer annotationcount){
		this.annotationcount = annotationcount;
	}
	public Integer getWatchcount(){
		return this.watchcount;
	}
	public void setWatchcount(Integer watchcount){
		this.watchcount = watchcount;
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