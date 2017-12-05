package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
  * @commons: t_annotation 数据持久化对象
  * @vision: 1.0.1 
  * @comment:  注释表
  */
public class Annotation implements Serializable{
	private static final long serialVersionUID = 1L;    
	public static final String ANNOTATIONID="annotationid";  //注释编号
	
	private String annotationid;  //注释编号
	private String fileid;  //文件编号
	private Integer linenum;  //行号。如果行号是0，表示是对这个文件进行注释
	private String userid;  //用户编号
	private String content;  //注释内容
	private Integer support;  //赞数
	private String state;  //状态:正常（2）、删除（4）、锁定（6）
	private Timestamp modtime;  //修改时间
	private Timestamp intime;  //入库时间
	
	public String getAnnotationid(){
		return this.annotationid;
	}
	public void setAnnotationid(String annotationid){
		this.annotationid = annotationid;
	}
	public String getFileid(){
		return this.fileid;
	}
	public void setFileid(String fileid){
		this.fileid = fileid;
	}
	public Integer getLinenum(){
		return this.linenum;
	}
	public void setLinenum(Integer linenum){
		this.linenum = linenum;
	}
	public String getUserid(){
		return this.userid;
	}
	public void setUserid(String userid){
		this.userid = userid;
	}
	public String getContent(){
		return this.content;
	}
	public void setContent(String content){
		this.content = content;
	}
	public Integer getSupport(){
		return this.support;
	}
	public void setSupport(Integer support){
		this.support = support;
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