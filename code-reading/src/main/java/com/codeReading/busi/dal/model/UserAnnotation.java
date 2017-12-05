package com.codeReading.busi.dal.model;

import java.io.Serializable;

/**
  * @commons: t_annotation 数据持久化对象
  * @vision: 1.0.1 
  * @comment:  注释表
  */
public class UserAnnotation implements Serializable{
	private static final long serialVersionUID = 1L;    
	public static final String ANNOTATIONID="annotationid";  //注释编号
	
	private String projectname;  //源码工程名
	private String path;  //文件路径
	private String filename;  //文件名
	private Integer linenum;  //行号。如果行号是0，表示是对这个文件进行注释
	private String content;  //注释内容
	private Integer support;  //赞数
	private String modtime;  //修改时间
	
	
	public Integer getLinenum(){
		return this.linenum;
	}
	public void setLinenum(Integer linenum){
		this.linenum = linenum;
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

	public String getModtime(){
		return this.modtime;
	}
	public void setModtime(String modtime){
		this.modtime = modtime;
	}
	public String getProjectname() {
		return projectname;
	}
	public void setProjectname(String projectname) {
		this.projectname = projectname;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

}