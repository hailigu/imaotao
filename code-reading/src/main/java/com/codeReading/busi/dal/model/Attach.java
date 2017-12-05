package com.codeReading.busi.dal.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Attach implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String attachid;  //附件号
	private String attachtype;  //附件类型[体系编号+具体类型]（如，用户证书01等，未知内容为00）
	private String path;  //路径
	private String attachname;  //原附件名
	private String filetype;  //附件类型(后缀)
	private String mimetype; //附件多媒体类型
	private Long size; //上传大小
	private String state;  //附件状态：2.正常 4.异常 7.删除 8.过期
	private String userid;  //上传用户号
	private Integer downloadcount;  //下载次数
	private Timestamp expiretime;  //过期时间
	private Timestamp modtime;  //修改时间
	private Timestamp intime;  //入库时间
	
	public String getAttachid() {
		return attachid;
	}
	public void setAttachid(String attachid) {
		this.attachid = attachid;
	}
	public String getAttachtype() {
		return attachtype;
	}
	public void setAttachtype(String attachtype) {
		this.attachtype = attachtype;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getAttachname() {
		return attachname;
	}
	public void setAttachname(String attachname) {
		this.attachname = attachname;
	}
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	public String getMimetype() {
		return mimetype;
	}
	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Integer getDownloadcount() {
		return downloadcount;
	}
	public void setDownloadcount(Integer downloadcount) {
		this.downloadcount = downloadcount;
	}
	public Timestamp getExpiretime() {
		return expiretime;
	}
	public void setExpiretime(Timestamp expiretime) {
		this.expiretime = expiretime;
	}
	public Timestamp getModtime() {
		return modtime;
	}
	public void setModtime(Timestamp modtime) {
		this.modtime = modtime;
	}
	public Timestamp getIntime() {
		return intime;
	}
	public void setIntime(Timestamp intime) {
		this.intime = intime;
	}
}
