package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
  * @commons: t_source_file 数据持久化对象
  * @vision: 1.0.1 
  * @comment:  源码表
  */
public class SourceFile implements Serializable{
	private static final long serialVersionUID = 1L;    
	public static final String FILEID="fileid";  //源码编号
	
	private String fileid;  //源码编号
	private String filename;  //文件名
	private String path;  //相对路径
	/**
	 * 所属源码工程
	 */
	private String owner; 
	private String superpath;  //上级目录
	private String type;  //类型: 0.文件夹 1.文件
	private Integer viewcount;  //阅览数
	private String state;  //状态: 2.正常 4.删除
	private Timestamp modtime;  //修改时间
	private Timestamp intime;  //入库时间
	
	public String getFileid(){
		return this.fileid;
	}
	public void setFileid(String fileid){
		this.fileid = fileid;
	}
	public String getFilename(){
		return this.filename;
	}
	public void setFilename(String filename){
		this.filename = filename;
	}
	public String getPath(){
		return this.path;
	}
	public void setPath(String path){
		this.path = path;
	}
	public String getOwner(){
		return this.owner;
	}
	/**
	 * @param owner
	 */
	public void setOwner(String owner){
		this.owner = owner;
	}
	public String getSuperpath(){
		return this.superpath;
	}
	public void setSuperpath(String superpath){
		this.superpath = superpath;
	}
	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
	public Integer getViewcount(){
		return this.viewcount;
	}
	public void setViewcount(Integer viewcount){
		this.viewcount = viewcount;
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