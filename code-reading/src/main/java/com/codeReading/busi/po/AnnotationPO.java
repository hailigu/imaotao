package com.codeReading.busi.po;

import java.io.Serializable;

public class AnnotationPO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String annotationid;
	private String path;  //文件编号
	private Integer linenum;  //行号。如果行号是0，表示是对这个文件进行注释
	private String userid;  //用户编号
	private String content;  //注释内容
	
	
	/**
	 * @return the annotationid
	 */
	public String getAnnotationid() {
		return annotationid;
	}
	/**
	 * @param annotationid the annotationid to set
	 */
	public void setAnnotationid(String annotationid) {
		this.annotationid = annotationid;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the linenum
	 */
	public Integer getLinenum() {
		return linenum;
	}
	/**
	 * @param linenum the linenum to set
	 */
	public void setLinenum(Integer linenum) {
		this.linenum = linenum;
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
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AnnotationPO [path=" + path + ", linenum=" + linenum + ", userid=" + userid + ", content=" + content
				+ "]";
	}
	
	
}
